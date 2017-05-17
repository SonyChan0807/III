from lib.crawler import Crawler
import time
from bs4 import BeautifulSoup
import re
import requests


def pttURL_crawler(res):
    board = "Soft_Job"
    keywd = '徵才'
    page_urls = []

    soup = BeautifulSoup(res.text, 'lxml')
    links = soup.select('div.title > a')
    # 爬取該頁面中每一篇有'keyword'的文章
    for link in links:
        title = link.text
        if ('徵才') in title:
            article_URL = "https://www.ptt.cc/" + link['href']
            # 只要爬到該keyword的文章寫入 page_urls
            page_urls.append(article_URL)
    return page_urls


def content_func(res):
    words = []
    article_dict = {}
    # 去除文章本文以外的標籤
    soup = BeautifulSoup(res.text, 'lxml')
    trash_link = soup.select('div.article-metaline > span') + soup.select('div.article-metaline-right > span') \
                 + soup.select('div.push') + soup.select('div#article-polling')
    for trash in trash_link:
        trash.decompose()
    # 過濾文章內文,並將有英文的部份全部改為小寫
    content = soup.select_one("#main-container")
    for script in content.find_all('script'):
        script.decompose()
    for a in content.select('a'):
        a.decompose()
    article_dict['content'] = content.text
    content = article_dict['content'].lower()
    # 過濾文章內容的特殊符號

    clearContent = content.replace('\n', '').replace('(', '').replace(')', '').replace(',', '')
    string_word = re.sub('"https://.*"|http://.*"', ' ', clearContent)
    # # 限定文章中只有英文是我們想要的內容
    words += list(set(re.findall('java script|objective c|visual basic|[A-Za-z.+#]+', string_word , re.IGNORECASE)))
    # words = list(set(re.findall('java script|objective c|visual basic|[A-Za-z]+[.+#]*?', string_word , re.IGNORECASE)))
    return words



URL = "https://www.ptt.cc/"
headers = {'User-Agent': 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)\
    Chrome/58.0.3029.96 Safari/537.36', "cookies": "over18=1"}
board = "Soft_Job"
#希望爬取ptt板面、但是頁面擷取的網址內容會缺乏URL,需要自行補上
res = requests.get(URL + "bbs/" + board +"/index.html" , headers = headers)
soup = BeautifulSoup(res.text , 'lxml')
bottons = soup.select('a.btn.wide')
#totalpage=上一頁的頁數+1
totalpage = int(bottons[1]['href'].split('index')[1].split('.')[0])+1


crawler = Crawler(open_thread=True)

page_url = URL + "bbs/" + board + "/index{}.html"
crawler.grab_pagelinks_th_auto(page_url, pttURL_crawler, totalpage, sleep_time=1, header=headers)

links = crawler.get_alinks()

print(links)
# Call grab_content_th_auto to get content page by page
crawler.grab_content_th_auto(links, content_func, sleep_time=2)

# Call get_counter to get word count result
print(crawler.get_counter().most_common())

with open('pttsoft_1_new.csv', 'w') as f:
    for lang, counts in crawler.get_counter().most_common():
        f.write('{},{}\n'.format(lang,counts))