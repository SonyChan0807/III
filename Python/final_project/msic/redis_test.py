import redis

que = redis.StrictRedis(host='192.168.114.10', port=6389, db=0)

que.lpush('newlist', 10)
que.lpush('newlist', 20)
que.lpush('newlist', 30)
que.lpush('newlist', 40)
que.lpush('newlist', 50)
que.lpush('newlist', 60)
que.lpush('newlist', 70)

print(que.lrange('newlist', 0, 5))
