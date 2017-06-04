import logging

# 第一種方式逐行設定

logger = logging.getLogger(__name__)
# logger.setLevel(logging.DEBUG)


# Setup logging format  Reference:  https://docs.python.org/3/library/logging.html   Chapter 16.6.7
formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

# File_handler
file_handler = logging.FileHandler('test.log')
file_handler.setFormatter(formatter)
file_handler.setLevel(logging.INFO)


# Stream_handler
stream_handler = logging.StreamHandler()
stream_handler.setFormatter(formatter)
stream_handler.setLevel(logging.DEBUG)

# Adding handler to logger
logger.addHandler(file_handler)
logger.addHandler(stream_handler)

# logging.basicConfig(filename='test2.log', level=logging.DEBUG, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')



# Test logging
logger.debug('Test debug')
logger.info('Test info')
logger.warning('test warning')
logger.error('test error')

# excption 與 error level 一樣 只是會多紀錄 Exception資訊 （建議使用）
# logger.exception('test error')
