import logging

logging.basicConfig(filename='test2.log', level=logging.INFO, format='%(asctime)s - %(name)s - %(levelname)s - %(message)s')


logging.info("""'This is logging debug \
                message'""")
