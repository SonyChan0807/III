from  bs4 import BeautifulSoup
import time
from selenium import webdriver
import logging
import datetime
from datetime import date
from selenium import webdriver
import sqlite3
import redis
import re


logger = logging.getLogger(__name__)
logger.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')
# Stream_handler
stream_handler = logging.StreamHandler()
stream_handler.setFormatter(formatter)
stream_handler.setLevel(logging.DEBUG)
logger.addHandler(stream_handler)


class Redisdb:
    host = '192.168.196.172'
    port = '6379'
    password = 'team1'


def convert_into_timestamp(day):
    dt = date.today()
    ts = time.mktime(dt.timetuple())
    data_date = ts - day * 24 * 60 * 60
    return data_date



def dict_init():
    property_list = ['網站', '連結', '標題', '廠牌', '型號', '車門數', '外觀顏色', '排氣量', '變速系統',
                     '配備', '里程數', '出廠年份', '車輛所在地', '時間', '價格', '認證', '燃料']
    new_dict = {k: '' for k in property_list}
    return new_dict


def get_content(url_str, browser):

    url = url_str.split('|')[0]
    tm = int(convert_into_timestamp(int(url_str.split('|')[1])))
    brand_catalog = url_str.split('|')[2]
    content_dict = dict_init()
    count = 3
    sleep_time = 5

    browser.get(url)
    while count:
        time.sleep(sleep_time)
        html = browser.page_source
        if 'carDetail' in html:
            soup = BeautifulSoup(html, 'lxml')
            content_dict['網站'] = 'abc'
            content_dict['連結'] = url
            content_dict['時間'] = tm
            if brand_catalog in ['AUDI', 'BENZ', 'BMW', 'FORD', 'HONDA', 'LEXUS', 'MAZDA', 'MITSUBISHI',
                                'NISSAN','PORSCHE','SUZUKI', 'SUBARU', 'TOYOTA', 'VOLVO', 'VW']:
                content_dict['認證'] = '原廠'
            else:
                content_dict['認證'] = ''

            # content_dict['廠牌']
            brand = soup.select_one('span.brand').text
            type_str = ''
            for span in soup.select_one('span.brand').find_next_sibling().select('span'):
                type_str += span.text

            content_dict['標題'] = brand + '-' + type_str
            content_dict['廠牌'] = brand
            content_dict['型號'] = type_str

            # content_dict['價格']
            price = soup.select_one('div.box > div > span > span').text
            content_dict['價格'] = price

            # 基本資料
            for li in soup.select('ul.basicInfo > li'):
                if li.select('span')[0].text in ['出廠年份', '里程數', '排氣量', '外觀顏色', '變速系統', '燃料', '車輛所在地']:
                    content_dict[li.select('span')[0].text] = li.select('span')[1].text

            # content_dict['配備']
            eqip = []

            for li in soup.select('ul.specCheckers > li.check'):
                eqip.append(li.text)
            content_dict['配備'] = '|'.join(eqip)

            logger.debug('get page: {}'.format(url))
            return content_dict
            break
        else:
            count -= 1
            sleep_time += 3
            logger.debug('fail to get page: {}'.format(url))
    if count == 0:
        logger.debug('fail to get page: {} after retrying 3 time. Push to abc_failed'.format(url))
        que.lpush('abc_failed', url)

def check_duplicates(url):
    cursor = conn.cursor()
    num = list(cursor.execute('SELECT * FROM abc_new_all WHERE url = ?', (url,)))
    if len(num) == 0:
        return False
    else:
        return True


def latest_update_time():
    cursor = conn.cursor()
    last_update = list(cursor.execute('SELECT max(posttime) FROM abc_new_all'))
    if last_update[0]:
        return 0
    else:
        return last_update[0][0]



def add_to_sqlite(content_dict):
    try:
        source = content_dict['網站']

        url = content_dict['連結']

        title = content_dict['標題']

        brand_str = content_dict['廠牌']
        brand = brand_transform(brand_str)

        model = content_dict['型號']

        doors = ''

        color = content_dict['外觀顏色'].replace('色', '')

        cc_str = content_dict['型號']
        if len(re.findall('[0-9]\.[0-9]', cc_str)) > 0:
            cc = int((re.findall('[0-9]\.[0-9]', cc_str))[0].replace('.', '')) * 100
        else:
            cc = 0

        transmission = content_dict['變速系統']
        equip = equip_transform(content_dict['配備'])

        mileag_str = content_dict['里程數']
        if len(mileag_str) > 0:
            mileage = int(mileag_str.replace('公里', ''))
        else:
            mileage = 0

        years = int(content_dict['出廠年份'])

        location = content_dict['車輛所在地']

        posttime = int(content_dict['時間'])

        price = float(content_dict['價格'])

        certificate = content_dict['認證']

        gasoline = content_dict['燃料']

        cursor = conn.cursor()
        if brand != '':
            cursor.execute('INSERT INTO abc_new_all VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?)',
                           (source, url, title, brand, model, doors, color, cc, transmission, equip,
                                mileage, years, location, posttime, price, certificate,gasoline))
            conn.commit()
        return True
    except Exception:
        logger.exception("Can't write to sql")
        conn.rollback()
        return False

def brand_transform(brand_str):
    brand_dict = {'VOLKSWAGEN': 'VW', 'VOLVO': 'VOLVO', 'TOYOTA': 'TOYOTA', 'FORD': 'FORD',
                     'MAZDA': 'MAZDA', 'MITSUBISHI': 'MITSUBISHI', 'BMW': 'BMW', 'NISSAN': 'NISSAN',
                     'AUDI': 'AUDI', 'M-BENZ': 'BENZ', 'PORSCHE': 'PORSCHE', 'HONDA': 'HONDA',
                     'SUZUKI': 'SUZUKI', 'LEXUS': 'LEXUS', 'SUBARU': 'SUBARU'}
    if brand_str in brand_dict.keys():
        return brand_dict[brand_str]
    else:
        return ''

def equip_transform(equip_str):
    equip_list = []
    equip_dict = {'安全氣囊': '安全氣囊', '倒車影像': '倒車影像', '倒車雷達': '倒車雷達', 'ACC': 'ACC主動式定速系統', '車側盲點偵測系統': '車側盲點偵測系統',
                  '車道偏移系統': '車道偏移系統', '免鑰': 'keyless免鑰系統', '電動摺疊後視鏡': '電動後視鏡', 'HID頭燈': 'HID頭燈', 'LED頭燈': 'LED頭燈',
                  '天窗': '天窗', '霧燈': '霧燈', '日行燈': '日行燈', '衛星導航': '衛星導航', 'DVD': 'DVD', '數位電視': '數位電視', '電動座椅': '電動座椅',
                  '動態巡跡防滑系統': 'TCS循跡系統', '定速': '定速', '恆溫空調': '恆溫空調', '鋁圈': '鋁圈', '皮椅': '皮椅'}
    for key in equip_dict.keys():
        if key in equip_str:
            equip_list.append(equip_dict.get(key))
    return ('|').join(equip_list)







if __name__ == '__main__':

    # Get url from redis
    que = redis.StrictRedis(host=Redisdb.host, port=Redisdb.port, db=0, password=Redisdb.password)

    browser = webdriver.PhantomJS('e:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe')
    conn = sqlite3.connect('E:\\Python\\pyCharm\\Crawler\\abc.db')
    brand_list = ['AUDI', 'M-BENZ', 'BMW', 'FORD', 'HONDA', 'LEXUS', 'MAZDA', 'MITSUBISHI',
                                'NISSAN','PORSCHE','SUZUKI', 'SUBARU', 'TOYOTA', 'VOLVO', 'VOLKSWAGEN']
    # Get url from redis
    try:
        latest_time = latest_update_time()
        while que.llen('abc_url') != 0:
            url_str = que.blpop('abc_url')[1].decode('utf8')
            # url format: https: // www.abccar.com.tw / buy - car / 698130 | 0 | TOYOTA
            day = int(convert_into_timestamp(int(url_str.split('|')[1])))
            brand_string = url_str.split('|')[2]
            if day > int(latest_time):
                if not check_duplicates(url_str.split('|')[0]):
                    content_dict = get_content(url_str, browser)
                    if content_dict:
                        add_to_sqlite(content_dict)
                    else:
                        logger.debug('lack of properties {} {}'.format(url_str, content_dict))
                else:
                    logger.debug('Url:{} exists in DB'.format(url_str))

    except Exception as e:
        conn.close()
        browser.quit()
        logger.exception('Some error happened')

    browser.quit()
