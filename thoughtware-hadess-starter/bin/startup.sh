#!/bin/sh
#-------------------------------------------------------------------------------------------------------------
#该脚本的使用方式为-->[sh startup.sh]
#该脚本可在服务器上的任意目录下执行,不会影响到日志的输出位置等
#-------------------------------------------------------------------------------------------------------------
DIRS=$(dirname "$PWD")

APP_MAIN="io.thoughtware.hadess.starter.HadessApplication"
JDK_VERSION=jdk-16.0.2
if [ -d "${DIRS}/embbed/${JDK_VERSION}" ]; then
    echo "使用内嵌jdk"
    JAVA_HOME="${DIRS}/embbed/${JDK_VERSION}"
else
    JAVA_HOME="/usr/local/jdk-17.0.7"
fi

find ${DIRS}/ -name '*.sh' | xargs dos2unix;

#-------------------------------------------------------------------------------------------------------------
#       系统运行参数
#-------------------------------------------------------------------------------------------------------------
#application.main.class="com.doublekit.platform.AccountSaasApplication"
DIR=$(cd "$(dirname "$0")"; pwd)
APP_HOME=${DIR}/..
APP_CONFIG=${APP_HOME}/conf/application-${env}.properties
APP_LOG=${APP_HOME}/logs

export APP_HOME

JAVA_OPTS="$JAVA_OPTS -server -Xms512m -Xmx512m -Xmn128m -XX:ParallelGCThreads=20 -XX:+UseParallelGC -XX:MaxGCPauseMillis=850 -Xloggc:$APP_LOG/gc.log -Dfile.encoding=UTF-8"
JAVA_OPTS="$JAVA_OPTS -DlogPath=$APP_LOG"
JAVA_OPTS="$JAVA_OPTS -Dconf.config=file:${APP_CONFIG}"
JAVA_opens="--add-opens java.base/java.lang=ALL-UNNAMED --add-opens java.sql/java.sql=ALL-UNNAMED"

CLASSPATH=${APP_HOME}/conf
for appJar in "$APP_HOME"/lib/*.jar;
do
   CLASSPATH="$CLASSPATH":"$appJar"
done

echo "JAVA_HOME="$JAVA_HOME
echo "JAVA_OPTS="$JAVA_OPTS
echo "CLASSPATH="$CLASSPATH
echo "APP_HOME="$APP_HOME
echo "APP_MAIN="$APP_MAIN

#-------------------------------------------------------------------------------------------------------------
#   程序开始
#-------------------------------------------------------------------------------------------------------------

APPLY=xpack-ce

enableApply(){

      APPLYDIR="$PWD"

      serverName=enable-${APPLY}.service

      applyserver=/etc/systemd/system/${serverName}

      if [ ! -e "${applyserver}" ]; then
cat << EOF >  ${applyserver}
[Unit]
Description=Start Tiklab Apply
After=network.target remote-fs.target nss-lookup.target

[Service]
EOF

echo Environment=\"DIR=${APPLYDIR}\" >> ${applyserver}

cat << EOF >> ${applyserver}
ExecStart=/bin/bash -c 'cd "\$DIR"; sh startup.sh'
Type=forking

[Install]
WantedBy=multi-user.target
EOF

  touch ${applyserver}
  chmod 644 ${applyserver}
  systemctl enable ${serverName}

  else
cat << EOF >  ${applyserver}
[Unit]
Description=Start Tiklab Apply
After=network.target remote-fs.target nss-lookup.target

[Service]
EOF

echo Environment=\"DIR=${APPLYDIR}\" >> ${applyserver}
cat << EOF >> ${applyserver}
ExecStart=/bin/bash -c 'cd "\$DIR"; sh startup.sh'
Type=forking

[Install]
WantedBy=multi-user.target
EOF
fi

}

enableApply

PID=0

getPID(){
    javaps=`$JAVA_HOME/bin/jps -l | grep $APP_MAIN`
    if [ -n "$javaps" ]; then
        PID=`echo $javaps | awk '{print $1}'`
    else
        PID=0
    fi
}

startup(){
    getPID
    echo "================================================================================================================"
    if [ $PID -ne 0 ]; then
        echo "$APP_MAIN already started(PID=$PID)"
        echo "================================================================================================================"
    else
        echo -n "starting $APP_MAIN"
        if [ ! -d "$APP_LOG" ]; then
            mkdir "$APP_LOG"
        fi

     nohup $JAVA_HOME/bin/java -Duser.timezone=Asia/Shanghai $JAVA_opens $JAVA_OPTS -classpath $CLASSPATH $APP_MAIN > /dev/null 2>&1 &

        for i in $(seq 5)
        do
          sleep 0.8
          echo -e  ".\c"
        done

        getPID

        if [ $PID -ne 0 ]; then
            echo "(PID=$PID)...[success]"
            echo "================================================================================================================"
        else
            echo "[failed]"
            echo "================================================================================================================"
        fi
    fi
}

startup