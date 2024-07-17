#!/bin/bash

dir=""
data=""
username=""
version=""
port=0

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
    -u)
      username=$2
      shift 2
      ;;
    -v)
      version=$2
      shift 2
      ;;
    -P)
      port=$2
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

  if [ -z "${version}" ]; then
      echo "Data version Name Cannot be empty"
      exit 1
  fi
  if [ "${port}" = 0 ]; then
      echo "Data port Name Cannot be empty"
      exit 1
  fi
}

#添加用户
add_user() {
  # 检查用户是否存在
  if id "$username" >/dev/null 2>&1; then
    return 0
  else
    useradd "$username"
  fi
}

add_user
result=$?

valid_user(){
  # 判断返回值并输出结果
  if [ $result -eq 0 ]; then
    echo "User added successfully"
  else
    echo "Failed to add user"
    exit 1
  fi
}

#授权
dir_chown(){
  echo "user ${username} database dir Authorization;"
  chown ${username} ${data}

  chown ${username} ${dir}
}

#初始化仓库
init_db(){
  echo "init data ${data}"
  output=$(su -c "${dir}/bin/pg_ctl init -D ${data}" -s /bin/sh ${username})
  if [ $? -eq 0 ]; then
    echo "$output"
  else
    echo "$output"
    echo "init Failed"
    exit 1;
  fi
}

#删除文件
delete_file(){
  echo "delete file postgres config "
  rm -rf ${data}/pg_hba.conf
  rm -rf ${data}/postgresql.conf
  rm -rf /tmp/.s.PGSQL.${port}
  rm -rf /tmp/.s.PGSQL.${port}.lock
}

#复制文件
copy_file(){
   echo "copy postgres config!"
   cp "${dir}/conf/pg_hba.conf" "${data}"
   cp "${dir}/conf/postgresql.conf" "${data}"
}

dir_chown

 for i in $(seq 2)
  do
    sleep 0.1
  done

init_db

for i in $(seq 5)
do
  sleep 0.1
done

delete_file

for i in $(seq 2)
do
  sleep 0.1
done

copy_file