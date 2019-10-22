#!/usr/bin/env bash
printf "$(date) ==== Starting ==== \n\n"
printf "JDK:$JAVA_HOME \n\n"

## Adjust memory settings if necessary
export JAVA_OPTS="-server -Xms1g -Xmx1g -Xss256k -Xmn600m -XX:MetaspaceSize=128m -XX:MaxMetaspaceSize=256m -XX:-OmitStackTraceInFastThrow -Duser.timezone=Asia/Shanghai -Dclient.encoding.override=UTF-8 -Dfile.encoding=UTF-8"

# 项目代码路径
export CODE_HOME=`pwd`

printf "PATH:$CODE_HOME \n"

# 设置依赖路径
export CLASSPATH="$CODE_HOME/classes:./lib/*"

# 启动类
export MAIN_CLASS=com.dm.eureka.SampleEurekaApplication

java $JAVA_OPTS -classpath $CLASSPATH $MAIN_CLASS > ./console.log 2>&1 & 

printf "\n-------------------------------------------\n"
