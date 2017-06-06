import os
import json
import logging.config

# 自動載入logging.json 設定檔 log 路徑可以在logging.json handlers.info_file_handler.filename 屬性設定

def setup_logging(
    # logging.json路徑
    default_path='/home/ubuntu/python/III/Python/final_project/Crawler/config/logging.json',
    default_level=logging.INFO,
    env_key='LOG_CFG'
):
    """Setup logging configuration

    """
    path = default_path
    value = os.getenv(env_key, None)
    if value:
        path = value
    if os.path.exists(path):
        print('read config file')
        with open(path, 'rt') as f:
            config = json.load(f)
        logging.config.dictConfig(config)
    else:
        logging.basicConfig(level=default_level)