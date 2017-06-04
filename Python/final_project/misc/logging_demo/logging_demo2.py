import logging
from set_logger import setup_logging

# 第二種方式利用json檔設定

# 建立logger物件
logger = logging.getLogger(__name__)

# Call setup_logging 設定logging
setup_logging()

# Test Logger
logger.debug('Test debug')
logger.info('Test info')
logger.warning('test warning')
logger.error('test error')

# excption 與 error level 一樣 只是會多紀錄 Exception資訊 （建議使用）
# logger.exception('test error')
