from bs4 import BeautifulSoup
import requests
import redis



def test_proxy(proxy_url):
    proxies = {'http': proxy_url,
              'https': proxy_url}

    try:
        res2 = requests.get('http://get.youripfast.com/?hl=zh-TW', proxies=proxies, timeout=3)
        print(res2.status_code)
        if res2.status_code == 200:
            # soup2 = BeautifulSoup(res2.text, 'lxml')
            # print(soup2.selectone('h2.remote_addr').text)
            print(proxy_url)
    except:
        pass

url = 'http://www.us-proxy.org/'
# url = 'http://www.socks-proxy.net/'

res = requests.get(url)

soup = BeautifulSoup(res.text, 'lxml')

trs = soup.select('tbody > tr')


# que = redis.StrictRedis(host='192.168.114.10', port=6389, db=0)
for tr in trs:
    tds = tr.select('td')
    proxy_url = 'http://{}:{}'.format(tds[0].text, tds[1].text)
    # proxy_url = 'socks5://{}:{}'.format(tds[0].text, tds[1].text)
    # proxy_url = '{}:{}'.format(tds[0].text, tds[1].text)
    # print(proxy_url)
    test_proxy(proxy_url)


    # que.lpush('proxylist', 'socks5://{}:{}'.format(tds[0].text, tds[1].text))
