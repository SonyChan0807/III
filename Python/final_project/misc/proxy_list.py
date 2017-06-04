from bs4 import BeautifulSoup
import requests
import argparse
import redis
from crawler_config import Redisdb


def test_proxy(proxy_url):
    """
    Proxy tester
    :param proxy_url: proxy_url
    :return: Boolean  if proxy is available return true
    """
    proxies = {'http': proxy_url, 'https': proxy_url}
    try:
        res2 = requests.get('http://get.youripfast.com/?hl=zh-TW', proxies=proxies, timeout=2)
        if res2.status_code == 200:
            return True
            # print(res2.status_code)
        else:
            return False
    except:
        return False

def main():
    parser = argparse.ArgumentParser(description='Choose proxy type ')
    parser.add_argument('-t',nargs='?', help="type: socks / http", const='http', default='http')
    args = parser.parse_args()

    if args == 'socks':
        url = 'http://www.socks-proxy.net/'
        address = 'socks5://{}:{}'
    else:
        url = 'http://www.us-proxy.org/'
        address = 'http://{}:{}'

    # que = redis.StrictRedis(host=Redisdb.host, port=Redisdb.port, db=0, password=Redisdb.password)
    que = redis.StrictRedis(host='10.120.37.118', port=6379, db=0, password='team1')
    try:
        while True:
            if que.llen('proxy_list') < 10:
                print('proxy number is less than 10')
                res = requests.get(url)
                soup = BeautifulSoup(res.text, 'lxml')
                trs = soup.select('tbody > tr')
                for tr in trs:
                    tds = tr.select('td')
                    proxy_url = address.format(tds[0].text, tds[1].text)
                    if test_proxy(proxy_url):
                        if que.llen('proxy_list') <= 20:
                            que.rpush('proxy_list', proxy_url)
                            print('push {} successfully'.format(proxy_url))
                        else:
                            break

    except KeyboardInterrupt:
        print('stop program by user interrupt')

if __name__ == '__main__':
    main()