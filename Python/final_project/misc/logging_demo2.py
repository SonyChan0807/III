import logging
from set_logger import setup_logging

# Setup logger using logging.json

logger = logging.getLogger(__name__)
setup_logging()

logger.debug('Test debug')
logger.info('Test info')
logger.warning('test warning')
logger.error('test error')
