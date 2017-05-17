from lib.crawler import Crawler
import time
from bs4 import BeautifulSoup
import re
import requests

# Define soup function and regular expression on your own
def content_func(res):
    words = []
    soup = BeautifulSoup(res.text, 'lxml')
    dds = soup.select('dl.dataList > dd')
    text = ''
    for dd in dds:
        for a in dd.find_all('a'):
            a.decompose()
        for img in dd.find_all('img'):
            img.decompose()
        text += dd.text
    words += list(set(re.findall('java script|objective c|visual basic|[A-Za-z.+#]+', text, re.IGNORECASE)))
    # words = list(set(re.findall('java script|objective c|visual basic|[A-Za-z]+[.+#]*?', text, re.IGNORECASE)))
    return words

def page_func(res):
    page_links = []
    soup = BeautifulSoup(res.text, 'lxml')
    links = soup.select('div.jbInfo > div > h3 > a')
    if res.status_code == 200 and len(links) != 0:
        for link in links:
            page_links.append('https:' + str(link['href']))
    return page_links

# Get total page from the website you want to crawl
HOST = 'https://www.1111.com.tw'
url = HOST + '/job-bank/job-index.asp?si=1&d0=140400,140200,140300&fs=1&ps=100&page=1'
res = requests.get(url)
soup = BeautifulSoup(res.text, 'lxml')
page = int(soup.select_one('div.pagedata').text.split('/')[1].split('頁')[0])


# Create an instance of Crawler class
crawler = Crawler(open_thread=True)

#  Call grab_pagelinks_th_auto to get all links
page_url = HOST + '/job-bank/job-index.asp?si=1&d0=140400,140200,140300&fs=1&ps=100&page={}'
crawler.grab_pagelinks_th_auto(page_url, page_func, page, sleep_time=1)

# Call get_alinks to get all links crawled from previous pages
links = crawler.get_alinks()

# Call grab_content_th_auto to get content page by page
crawler.grab_content_th_auto(links, content_func, sleep_time=1)

# Call get_counter to get word count result
print(crawler.get_counter().most_common())

with open('1111_1_new.csv', 'w') as f:
    for lang, counts in crawler.get_counter().most_common():
        f.write('{},{}\n'.format(lang,counts))