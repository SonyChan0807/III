from  bs4 import BeautifulSoup
import requests
import time
from selenium import webdriver
import json
import logging
import redis

from selenium.common.exceptions import TimeoutException
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By


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



def get_brand_url():
    res = requests.get('https://www.abccar.com.tw/api/Navigation')
    data = json.loads(res.text)
    target_list = ['AUDI', 'BENZ', 'BMW', 'FORD', 'HONDA', 'LEXUS', 'MAZDA', 'MITSUBISHI', 'NISSAN', 'PORSCHE',
                   'SUZUKI', 'SUBARU', 'TOYOTA', 'VOLVO', 'VOLKSWAGEN', '基隆市汽車商業同業公會', '台北市汽車商業同業公會',
                   '新北市汽車商業同業公會', '桃園市汽車商業同業公會', '苗栗縣汽車商業同業公會', '台中市汽車商業同業公會',
                   '南投縣汽車商業同業公會', '台南市汽車商業同業公會', '臺南市直轄市汽車商業同業公會', '高雄市汽車商業同業公會',
                   '宜蘭縣汽車商業同業公會', '中華民國汽車商業同業公會全聯會']
    brand_urls = []
    for i in range(0, 3):
        for item in data[0]['Childs'][i]['Childs']:
            if item.get('Name') in target_list:
                brand_urls.append('https://www.abccar.com.tw' + item.get('Url') + '&Page={}|' + item.get('Name'))
    logger.debug(brand_urls)
    return brand_urls

def get_page_no(url, browser):
    browser.get(url.format(1))
    sleep_time = 5
    count = 3
    logger.info('getting page fro {}'.format(url))
    while True:
        try:
            time.sleep(sleep_time)
            html = browser.page_source
            if 'searchUtilBtn' in html:
                soup = BeautifulSoup(html, 'lxml')
                count_div = soup.select('span.resultCounter')
                if len(count_div) == 1:
                    logger.debug(count_div[0].text.replace(',', ''))
                    number = int(count_div[0].text.replace(',', ''))
                    page = int(number / 36) + 1
                    logger.debug('page: {}'.format(page))
                    return page
                else:
                    return 1
            else:
                logger.debug('retry get_page_no')
                sleep_time +=5
                count -= 1
        except Exception as e:
            logger.exception("failed to get page_no action: retry")



def url_parser(html,brand):
    url_list = []
    soup = BeautifulSoup(html, 'lxml')
    divs = soup.select('div.ht-thumbnail.thumbnail.ht-thumbnail-default.listMode')
    for div in divs:
        link = div.select_one('a')['href']
        day_str = div.select_one('a > div > p > small').text
        day = 0
        if '天' in day_str:
            day = int(day_str.split('天')[0])
        elif '小時前' in day_str:
            hour = int(day_str.split('小時前')[0])
            if hour <= 24:
                day = 0
            else:
                day = 1
        url_list.append('https://www.abccar.com.tw' + link + '|' + str(day) + '|' + brand)
    # logger.debug(url_list)
    logger.debug('url_list_length: {}'.format(len(url_list)))
    return url_list

def get_urls(url, brand, page_no, browser):
    htmls = []
    for i in range(1, page_no + 1):
        sleep_time = 5
        browser.get(url.format(i))
        count = 3
        while count:
            time.sleep(sleep_time)
            html = browser.page_source
            if 'ht-thumbnail thumbnail ht-thumbnail-default listMode' in html:
                htmls.append(html)
                break
            else:
                count -= 1
                sleep_time += 5
                logger.debug('fail to load page: {} retry: {}'.format(i, count))
                continue
    urls = []
    for h in htmls:
        urls += url_parser(h, brand)
    return urls

def write_to_csv(links):
    with open('abc1.csv', 'a') as f:
        for link in links:
            f.write(link + '\n')

def push_to_redis(links):
    for link in links:
        que.lpush('abc_url', link)

if __name__ == '__main__':
    browser = webdriver.PhantomJS('e:\\phantomjs-2.1.1-windows\\bin\\phantomjs.exe')
    brand_list = get_brand_url()

    que = redis.StrictRedis(host=Redisdb.host, port=Redisdb.port, db=0, password=Redisdb.password)

    for brand_str in brand_list[2:]:
        brand = brand_str.split('|')[1]
        brand_url = brand_str.split('|')[0]
        logger.debug(brand_url)
        page_no = get_page_no(brand_url, browser)
        url_list = get_urls(brand_url, brand, page_no, browser)  # temporary storage
        push_to_redis(url_list)
        logger.debug('brand: {} is finished'.format(brand_url))
    
    browser.close()



