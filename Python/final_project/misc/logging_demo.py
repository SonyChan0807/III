import logging

logger = logging.getLogger(__name__)
# logger.setLevel(logging.DEBUG)

formatter = logging.Formatter('%(asctime)s - %(name)s - %(levelname)s - %(message)s')

# File_handler
file_handler = logging.FileHandler('test3.log')
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


logger.debug("""'This is logging debug message'""")
logger.info("info message")
logger.exception('Record error message')