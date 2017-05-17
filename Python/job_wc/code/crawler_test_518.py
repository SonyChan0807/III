from lib.crawler import Crawler
import time
from bs4 import BeautifulSoup
import re
import requests

def page_func(res):
    links = []
    soup = BeautifulSoup(res.text, 'lxml')
    rows = soup.select('li.title > a')
    for row in rows:
        links.append(row['href'])
    return links


def content_func(res):
    words = []
    soup = BeautifulSoup(res.text, 'lxml')
    for dd in soup.select('div.job-detail-box'):
        string_word = re.sub('"https://.*"|http://.*"', '', dd.text.replace('\n', ' ').replace('、', ' '))
        words += list(set(re.findall('object c|visual basic|[A-Za-z.+#]+', string_word, re.IGNORECASE)))
    return list(set(words))

# Get total page from the website you want to crawl
url = 'https://www.518.com.tw/job-index.html?i=1&am=1&ab=2032001,2032002,&i=1&am=1&ai=1&scr=0&ac='
res = requests.get(url)
soup = BeautifulSoup(res.text, 'lxml')
page = int(soup.select('span.pagecountnum > span')[0].text.split('/')[1])


# Create an instance of Crawler class
crawler = Crawler(open_thread=True)

#  Call grab_pagelinks_th_auto to get all links
page_url = 'https://www.518.com.tw/job-index-P-{}.html?i=1&am=1&ab=2032001,2032002,&ai=1,'
crawler.grab_pagelinks_th_auto(page_url, page_func, page, sleep_time=1)

# Call get_alinks to get all links crawled from previous pages
links = crawler.get_alinks()

# Call grab_content_th_auto to get content page by page
crawler.grab_content_th_auto(links, content_func, sleep_time=2)

# Call get_counter to get word count result
print(crawler.get_counter().most_common())

with open('518_1_new.csv', 'w') as f:
    for lang, counts in crawler.get_counter().most_common():
        f.write('{},{}\n'.format(lang,counts))