from bs4 import BeautifulSoup
import requests
import redis

proxy_url = 'http://54.71.65.172:80'

proxies = {'http': proxy_url,
            'https': proxy_url}

res = requests.get('http://get.youripfast.com/?hl=zh-TW', proxies=proxies)
soup2 = BeautifulSoup(res.text, 'lxml')
print(soup2.text)
