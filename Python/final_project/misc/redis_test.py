import redis
import time
que = redis.StrictRedis(host='192.168.114.10', port=6379, db=0)

while True:
    proxy_url = que.blpop('proxy_list')[1]
    print(proxy_url)
    print(que.llen('proxy_list'))
    time.sleep(1)
