from lib.crawler import Crawler
import time
from bs4 import BeautifulSoup
import re
import requests
from selenium import webdriver
from selenium.webdriver.common.keys import Keys


# Handle javacript web page
# open browser
browser = webdriver.Chrome()
browser.get('http://www.yes123.com.tw/admin/index.asp')

# input search keywords
browser.find_element_by_id('find_key1').clear()
browser.find_element_by_id('find_key1').send_keys('軟體 工程師')

time.sleep(2)
# Click search button
browser.find_element_by_class_name('n_serch_btn').click()

time.sleep(2)
counter = 0
retry = 3
alinks = []
while True:
    try:
        counter += 1
        browser.find_element_by_tag_name('body').send_keys(Keys.END)
        links = [link.get_attribute('href') for link in browser.find_elements_by_class_name('jobname')]
        alinks += links
        # with open('link_yes123_2.csv', 'w') as f:
        #     f.write('\n'.join(links) + '\n')
        print('[Debug] links of page %d are saved' % counter)
        browser.find_element_by_link_text('>').click()
        time.sleep(0.5)
    except Exception as e:
        print(e)
        retry -= 1
        if retry == 0:
            break
print('The end of pages is No.%d' %(counter))
print('Browser will quit!')
browser.quit()

def content_func(res):
    words = []
    soup = BeautifulSoup(res.text, 'lxml')
    content = ''
    divs = soup.select('div.comp_detail')
    del divs[-1]
    for div in divs:
        content += div.text.replace('\n', '').replace('\xa0', '')
    words += list(set(re.findall('java script|objective c|visual basic|[A-Za-z.+#]+', content, re.IGNORECASE)))
    return words

# Create an instance of Crawler class
crawler = Crawler(open_thread=True)

# Call grab_content_th_auto to get content page by page
crawler.grab_content_th_auto(alinks, content_func, sleep_time=1)

# Call get_counter to get word count result
print(crawler.get_counter().most_common())

with open('yes123_1_new.csv', 'w') as f:
    for lang, counts in crawler.get_counter().most_common():
        f.write('{},{}\n'.format(lang,counts))

