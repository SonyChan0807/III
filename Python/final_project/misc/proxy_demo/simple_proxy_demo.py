from bs4 import BeautifulSoup
import requests

# 測試proxy
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
        else:
            return False
    except:
        return False

# 爬取可用proxy存成 proxy_list
url = 'http://www.us-proxy.org/'
address = 'http://{}:{}'
res = requests.get(url)
soup = BeautifulSoup(res.text, 'lxml')
trs = soup.select('tbody > tr')
proxy_list = []
for tr in trs:
    tds = tr.select('td')
    proxy_url = address.format(tds[0].text, tds[1].text)
    if test_proxy(proxy_url):
        proxy_list.append(proxy_url)


