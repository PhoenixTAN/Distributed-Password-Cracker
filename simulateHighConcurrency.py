import gevent
from gevent import monkey
monkey.patch_all()

import time
import json
from urllib.error import URLError
import requests
import hashlib
import string
import random




url = 'http://192.86.139.65:8080/getPassword'
headers = {'Content-Type': 'application/json; charset=utf-8'}


def make_data():
    """the body of post request"""
    charlist = [random.choice(string.ascii_letters) for i in range(5)]
    while(not ((charlist[0] >= 'a' and charlist[0] <= 'c') and (charlist[1] >= 'a' and charlist[1] <= 'c'))):
        charlist = [random.choice(string.ascii_letters) for i in range(5)]
    chars = ''.join(charlist)
    print(chars)
    # create md5 object
    hl = hashlib.md5()
    hl.update(chars.encode(encoding='utf-8'))
    print(hl.hexdigest())
    data = {
        'MD5Password': hl.hexdigest(),
        'workerNum': '8'
    }
    return json.dumps(data)


def run():
    data = make_data()
    try:
        resp = requests.post(url=url, data=data, headers=headers)
        print("password:\n", resp.content.decode())

    except URLError as e:
        print('request', e)
    except Exception as e:
        print('request error：', e)


def call_gevent(count):
    """use gevent to simulate high concurrency"""
    begin_time = time.time()
    run_gevent_list = []
    for i in range(count):
        print('--------------%d--Test-------------' % i)
        run_gevent_list.append(gevent.spawn(run))
    gevent.joinall(run_gevent_list)
    end = time.time()
    print('single test time（average）s:', (end - begin_time) / count)
    print('cumulative testing time s:', end - begin_time)


if __name__ == '__main__':
    # send 100 concurrent requests
    test_count = 100
    call_gevent(count=test_count)