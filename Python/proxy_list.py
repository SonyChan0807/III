from bs4 import BeautifulSoup
import requests
import redis

url = 'http://www.socks-proxy.net/'

res = requests.get(url)

soup = BeautifulSoup(res.text, 'lxml')

trs = soup.select('tbody > tr')


que = redis.StrictRedis(host='192.168.114.10', port=6389, db=0)
for tr in trs:
    tds = tr.select('td')
    print('socks5://{}:{}'.format(tds[0].text, tds[1].text))
    que.lpush('proxylist', 'socks5://{}:{}'.format(tds[0].text, tds[1].text))
