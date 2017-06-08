from bs4 import BeautifulSoup
import requests
import time
import sqlite3
import logging
from final_project.Crawler.lib.set_logger import setup_logging
from random import randint
import redis
import datetime
from hashlib import blake2b


referers = ['tw.yahoo.com', 'www.google.com', 'http://www.msn.com/zh-tw/', 'http://www.pchome.com.tw/']
user_agents = [
    'Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36',
    'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9',
    'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36',
    'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)']

brand_list = ['AUDI', 'BENZ', 'BMW', 'FORD', 'HONDA', 'LEXUS', 'MAZDA', 'MITSUBISHI',
              'NISSAN', 'PORSCHE', 'SUZUKI', 'SUBARU', 'TOYOTA', 'VOLVO', 'VW']


class Redisdb:
    host = '192.168.114.10'
    port = '6379'
    password = 'team1'

def gen_headers():
    headers = {'User-Agent': user_agents[randint(0, len(user_agents) - 1)],
               'Referer': referers[randint(0, len(referers) - 1)]}
    return headers
def gen_proxies():
    proxy_url = que.blpop('proxy_list')[1].decode('utf8')
    proxy_gen = {'http': proxy_url, 'https': proxy_url}
    print(proxy_url)
    return proxy_gen

def get_content(url):
    logger.info(url)
    new_url = url.split('|')[0]
    headers = gen_headers()
    res = requests.get(new_url, headers=headers, proxies=proxy)
    soup = BeautifulSoup(res.text, 'lxml')

    contents = []
    content_table = soup.select_one('table > tr').select_one('td').select('table')[5]
    for tr in content_table.select('tr'):
        for td in tr.select('td'):
            contents.append(td.text)

    # price
    price_tr = soup.select_one('span.STYLE1').parent.parent.find_next_sibling()
    contents.append('價格：' + price_tr.select_one('span').text)

    # date
    date_table = soup.select_one('table > tr').select_one('td').select('table')[2]
    date_str = date_table.select_one('tr').select('td')[1].text
    if len(date_str) == 0:
        date_str = url.split('|')[1]
    else:
        date_str = date_str[5:]
    tm = int(datetime.datetime.strptime(date_str, "%Y-%m-%d").timestamp())
    contents.append('時間：' + str(tm))

    # title
    title_table = soup.select_one('table > tr').select_one('td').select('table')[3]
    contents.append('標題：' + title_table.select_one('tr').select_one('td').text)

    # Eqip
    eqip_table = soup.select_one('table > tr').select_one('td').select('table')[13]
    eqips = []
    for idx, tr in enumerate(eqip_table.select('tr')):
        if idx % 7 != 0:
            item = tr.select('td')
            if item[0].select_one('img')['src'] == 'images/icon_1.jpg':
                eqips.append(item[2].text)
    contents.append('配備：' + '|'.join(eqips))

    content_dict = {k.split('：')[0]: k.split('：')[1] for k in contents if len(k) > 0}
    content_dict['網站'] = 'icars'
    content_dict['連結'] = new_url
    return content_dict

def add_to_sqlite(content_dict):

    source = content_dict['網站']
    url = content_dict['連結']
    title = content_dict['標題']
    brand = content_dict['廠牌']
    model = content_dict['型號']
    doors = content_dict['車門']
    color = content_dict['色系']
    cc = content_dict['排氣量']
    transmission = content_dict['排擋方式']
    equip = content_dict['配備']
    mileage = content_dict['行駛里程']
    years = content_dict['年份']
    location = content_dict['所在地']
    posttime = content_dict['時間']
    price = content_dict['價格']
    certificate = ''
    try:
        conn = sqlite3.connect('/home/ubuntu/python/III/Python/final_project/Crawler/icars.db')
        cursor = conn.cursor()
        cursor.execute('INSERT INTO icars VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)',
                       (source, url, title, brand, model, doors, color, cc, transmission, equip,
                        mileage, years, location, posttime, price, certificate))
        conn.commit()
        conn.close()
        return True
    except Exception:
        conn.rollback()
        return False
    finally:
        conn.close()



def check_duplicates(content_dict):
    conn = sqlite3.connect('/home/ubuntu/python/III/Python/final_project/Crawler/icars.db')
    cursor = conn.cursor()

    title = content_dict['標題']
    color = content_dict['色系']
    cc = content_dict['排氣量']
    equip = content_dict['配備']
    location = content_dict['所在地']

    num = list(
        cursor.execute('SELECT * FROM icars WHERE title = ? AND cc = ? AND color = ?  AND equip = ? AND location = ?',
                       (title, cc, color, equip, location)))
    if len(num) == 0:
        return False
    else:
        return True

def hash_func(url):
    h = blake2b(digest_size=20)
    h.update(str.encode(url))
    return h.hexdigest()

if __name__ == '__main__':

    # Setup varibles
    logger = logging.getLogger(__name__)
    setup_logging()

    # Get url from redis
    que = redis.StrictRedis(host=Redisdb.host, port=Redisdb.port, db=0, password=Redisdb.password)

    # Get content
    proxy = gen_proxies()

    while que.llen('icar_list') != 0:
        count = 3
        url = que.blpop('icar_list')[1].decode('utf8')
        while count:
            try:
                content_dict = get_content(url)
                if not check_duplicates(content_dict):
                    add_to_sqlite(content_dict)
                else:
                    logger.info('same car exist pass')
                    break
            except IndexError as e:
                logger.exception(e)
                count -= 1
            except (requests.exceptions.ProxyError, ConnectionRefusedError) as e:
                logger.exception(e)
                proxies = gen_proxies()
            except Exception as e:
                count -= 1
                logger.exception('url:' + url + ' count' + str(count))
        # Push to failed list
        if count == 0:
            que.lpush('icar_failed', url)

# redis.exceptions.ConnectionError
# ConnectionRefusedError
# requests.exceptions.ProxyError
# IndexError