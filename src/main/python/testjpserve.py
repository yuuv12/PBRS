"""
Date: 5/24/2024
Author: lu0qlng
Description: 测试与python的相性
"""
import sys


def func(a1, b):
    return a1 + b


if __name__ == '__main__':
    a = []
    for i in range(len(sys.argv)):
        print(sys.argv[i]+" index:"+str(i))
    print("aaa")
