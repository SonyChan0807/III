from lib.crawler import Crawler
import time
from bs4 import BeautifulSoup
import re
import requests


def pttURL_crawler(res):
    board = "Tech_job"
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


# def






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
crawler.grab_pagelinks_th_auto(page_url, pttURL_crawler, totalpage, sleep_time=0.2, header=headers)

links = crawler.get_alinks()

print(links)