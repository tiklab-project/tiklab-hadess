#!/bin/bash

dir=""
data=""
username=""
port=""

#解析参数
echo "Parse startup parameters"
for arg in "$@"; do
  case $arg in
    -d)
      dir=$2
      shift 2
      ;;
    -D)
      data=$2
      shift 2
      ;;
    -P)
      port=$2
      shift 2
      ;;
    -u)
      username=$2
      shift 2
      ;;
  esac
done

#效验参数
valid_parameters(){
  if [ -z "${dir}" ]; then
    echo "Apply address Cannot be empty"
    exit 1
  fi

  if [ -z "${data}" ]; then
      echo "Data data Name Cannot be empty"
      exit 1
  fi

  if [ -z "${username}" ]; then
      echo "Data username Name Cannot be empty"
      exit 1
  fi
}

#启动
restart_postgres(){
    echo "restart postgres data:${data}"
    nohup su -c "${dir}/bin/pg_ctl restart -D ${data}" -s /bin/sh ${username} >${dir}/log.log 2>&1 &
}

valid_parameters
restart_postgres







