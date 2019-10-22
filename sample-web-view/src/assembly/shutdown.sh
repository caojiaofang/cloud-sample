#!/bin/sh
#要杀死的端口号
port=8090
#根据端口号查询对应的pid
pid=$(netstat -nlp | grep :$port | awk '{print $7}' | awk -F"/" '{ print $1 }');

#杀掉对应的进程，如果pid不存在，则不执行
if [  -n  "$pid"  ];  then
    kill  -9  $pid;
    printf "SUCCESS\n\n"
else
	printf "error\n\n"
fi