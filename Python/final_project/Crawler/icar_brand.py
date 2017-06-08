from bs4 import BeautifulSoup
import requests
import time
import re
import logging
from final_project.Crawler.lib.set_logger import setup_logging
from random import randint
import redis

referers = ['tw.yahoo.com', 'www.google.com', 'http://www.msn.com/zh-tw/', 'http://www.pchome.com.tw/']
user_agents = ['Mozilla/5.0 (X11; CrOS x86_64 8172.45.0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.64 Safari/537.36',
             'Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_2) AppleWebKit/601.3.9 (KHTML, like Gecko) Version/9.0.2 Safari/601.3.9',
              'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/47.0.2526.111 Safari/537.36',
              'Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)']
cookie = 'PHPSESSID=5rvg4cdkfpbd9skuntk0ifo2r2; _gat=1; _ga=GA1.3.815060608.1496624322; _gid=GA1.3.657905158.1496852224'

brand_list = ['AUDI', 'BENZ', 'BMW', 'FORD', 'HONDA', 'LEXUS', 'MAZDA', 'MITSUBISHI',
              'NISSAN', 'PORSCHE', 'SUZUKI', 'SUBARU', 'TOYOTA', 'VOLVO', 'VW']
# brand_list = [ 'LEXUS','MAZDA', 'TOYOTA', 'VOLVO', 'VW']
# 'LEXUS''MAZDA',

class Redisdb:
    host = '192.168.114.10'
    port = '6379'
    password = 'team1'

def gen_headers():
    headers = {'User-Agent': user_agents[randint(0, len(user_agents) - 1)],
               'Referer': referers[randint(0, len(referers) - 1)]}
    return headers

def get_brand_url(url):
    brands = []
    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')
    a_list = soup.select('div.search_list_cont > ul > li > span > a')

    for a in a_list:
        if a.text in brand_list:
            brands.append((a.text, a['href']))
    return brands

def get_car_innerurl(url):
    count = 5
    try:
        while count:
            res = requests.get(url, headers=headers)
            soup = BeautifulSoup(res.text, 'lxml')
            trs = soup.select('div.search_list_cont > table > tr')
            for idx, tr in enumerate(trs):
                if idx > 1:
                    que = redis.StrictRedis(host=Redisdb.host, port=Redisdb.port, db=0, password=Redisdb.password)
                    car_str = 'http://cars.icars.com.tw' + tr.select_one('td').select_one('a')['href'] + '|' + \
                              tr.select('td')[6].text
                    que.rpush('icar_list', car_str)

            break
    except Exception as e:
        count -= 1
        logger.exception(url + 'count=' + str(count))
def get_page_no(url):
    res = requests.get(url, headers=headers)
    soup = BeautifulSoup(res.text, 'lxml')
    div = soup.select_one('div.search_list_next')
    for a in div.select('a'):
        a.decompose()
    for span in div.select('span'):
        span.decompose()
    for select in div.select('select'):
        select.decompose()
    page_no = re.findall('\d+', div.text)
    return page_no[0]


if __name__ == '__main__':

    # Setup varibles
    logger = logging.getLogger(__name__)
    setup_logging()
    headers = gen_headers()
    url = 'http://cars.icars.com.tw/search_list.php'

    # Get url from 15 brands
    brand_urls = get_brand_url(url)
    logger.debug(brand_urls)

    # Get brand links
    for brand in brand_urls:
        brand_no = brand[1].split('.')[0][6:]
        # brand_url = 'http://cars.icars.com.tw/search_show.php?brand={}&psize=50&porder=&psc=&skey=&stype=&page={}'
        brand_url = 'http://cars.icars.com.tw/{}'
        page_no = get_page_no(brand_url.format(brand[1]))
        logger.info(brand[0] + ':' + page_no)

        for i in range(1, int(page_no) + 1):
            brand_url = 'http://cars.icars.com.tw/search_show.php?brand={}&psize=50&porder=&psc=&skey=&stype=&page={}'\
                .format(brand_no, i)
            headers = gen_headers()
            get_car_innerurl(brand_url)
            # time.sleep(1)
            logger.info('{} page {} is finished'.format(brand, i))
