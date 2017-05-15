from lib.crawler import Crawler
import time
from bs4 import BeautifulSoup
import re
import requests

def page_func(res):
    soup = BeautifulSoup(res.text, 'lxml')
    links = soup.select(".j_cont > ul > li  > div > a")

    page_links = []
    if len(links) != 0:
        for link in links:
            if ("jobno" in link['href']):
                page_links.append('https://www.104.com.tw' + link['href'])
    else:
        raise ValueError('Loading page too fast!! Will Reload page again!')
    return page_links
def content_func(res):
    words = []
    soup = BeautifulSoup(res.text, 'lxml')
    for dl in soup.select('div.content > dl'):
        string_word = re.sub('"https://.*"|http://.*"', '', dl.text.replace('\n', ' ').replace('、', ' '))
        words += (re.findall('java script|objective c|visual basic|[a-z]+[.+#]*', string_word, re.IGNORECASE))
    return list(set(words))


# Create an instance of Crawler class
crawler = Crawler(open_thread=True)

#  Call grab_pagelinks_th_auto to get all links
page_url = 'https://www.104.com.tw/jobbank/joblist/joblist.cfm?jobsource=n104bank1&ro=0&\
           jobcat=2007000000&order=2&asc=0&page={}&psl=N_A'
crawler.grab_pagelinks_th_auto(page_url, page_func, 20, sleep_time=1)

# Call get_alinks to get all links crawled from previous pages
links = crawler.get_alinks()



# Call grab_content_th_auto to get content page by page
crawler.grab_content_th_auto(links, content_func, sleep_time=1)

# Call get_counter to get word count result
print(crawler.get_counter().most_common())