import logging
import requests
import time
from collections import Counter
import threading
from bs4 import BeautifulSoup
import re


class Crawler:

    HEADER = {'User-Agent': """Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko)
        Chrome/58.0.3029.96 Safari/537.36"""}

    def __init__(self,retry_time=5, wc=Counter(), open_thread=False):
        self.retry_time = retry_time
        self.open_thread = open_thread
        if open_thread:
            self.lock = threading.Lock()
        self.wc = wc
        self.alinks = []

    def grab_content(self, url, content_crawler, page_idx=None, header=None, sleep_time=0.5):
        """
        :param url: url
        :param content_crawler: Customzied function of BeautifulSoup
        :param page_idx: page counts from for loop, defult: none
        :param header: The header required from website, defult: chrome useragent
        :param sleep_time:  Time interval betweeb retry in second, defult: 0.5
        :return: none
        """
        if header:
            header = self.HEADER
        retry = self.retry_time
        while retry:
            try:
                res = requests.get(url, headers=header)
                if res.status_code == 200:
                    words = content_crawler(res)
                    self.word_counter(words)
                    logging.debug('Current status: {}'.format(page_idx))
                    break
                else:
                    self.logging_warning(page_idx, retry=6 - retry)
                    retry -= 1
                    time.sleep(sleep_time)
            except Exception as e:
                self.logging_exception(page_idx, 6 - retry)
                retry -= 1
                time.sleep(sleep_time)
        if retry == 0:
            self.logging_warning(page_idx, url=url)

    def grab_content_th(self, url, content_crawler, page_idx, header=None, sleep_time=0.5):
        if not self.open_thread:
            raise ValueError('Please set open_thead=True while creating Crawler instance')
        thread = threading.Thread(target=self.grab_content, args=(url, content_crawler)
                                  , kwargs={'page_idx':page_idx,'header': header, 'sleep_time': sleep_time})
        thread.start()
        return thread

    def grab_pagelinks(self, url, page_crawler, page_idx=None, header=None, sleep_time=0.5):
        """
        :param url: url
        :param page_crawler: Customzied function of BeautifulSoup
        :param page_idx: page counts from for loop, defult: none
        :param header: The header required from website, defult: chrome useragent
        :param sleep_time:  Time interval betweeb retry in second, defult: 0.5
        :return: none
        """
        if header:
            header = self.HEADER
        retry = self.retry_time
        while retry:
            try:
                res = requests.get(url, headers=header)
                if res.status_code == 200:
                    links = page_crawler(res)
                    self.alinks += links
                    logging.debug('Current status: {}'.format(page_idx))
                    break
                else:
                    self.logging_warning(page_idx, retry= 6 - retry)
                    retry -= 1
                    time.sleep(sleep_time)
            except Exception as e:
                self.logging_exception(page_idx, 6 - retry)
                retry -= 1
                time.sleep(sleep_time)
        if retry == 0:
            self.logging_warning(page_idx, url=url)

    def grab_pagelinks_th(self, url, page_crawler, page_idx=None, header=None, sleep_time=0.5):
        if not self.open_thread:
            raise ValueError('Please set open_thead=True while creating Crawler instance')
        thread = threading.Thread(target=self.grab_pagelinks, args=(url, page_crawler,)
                                  , kwargs={'page_idx':  page_idx,'header': header, 'sleep_time': sleep_time})
        thread.start()
        return thread

    def word_counter(self, words):
        if self.open_thread:
            with self.lock:
                for lan in words:
                    if lan in self.wc:
                        self.wc[lan] += 1
                    else:
                        self.wc[lan] = 1
        else:
            for lan in words:
                if lan in self.wc:
                    self.wc[lan] += 1
                else:
                    self.wc[lan] = 1

    def logging_warning(self, page_idx, retry=None, url=None):
        if url:
            print('Error: fail to get conent at page: {}, link: {}'.format(page_idx, url))
            # logging.warning('Error: fail to get conent at page: {}, link: {}'.format(page_idx, url))
        elif retry:
            print('Cannot retrieve content from page {} Retry: {}'.format(page_idx, retry))
            # logging.warning('Cannot retrieve content from page {} Retry: {}'.format(page_idx))

    def logging_exception(self, page_idx, retry):
        print('Cannot retrieve content from page {} Retry: {}'.format(page_idx, retry))
        # logging.exception('Exception occurred! Cannot retrieve content from page {} Retry: {}'.format(page_idx,retry))




    def get_counter(self):
        """
        Get the counter object
        :return: Counter object
        """
        return self.wc

    def get_alinks(self):
        """
        Get a list of all links from website
        :return:  list of all links
        """
        return self.alinks

