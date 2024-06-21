#!/bin/bash

dir=""
username=""
db=""
pwd=""
port=0

#解析参数
echo "Parse startup parameters"
for arg in "$@"; do
  case $arg in
    -d)
      dir=$2
      shift 2
      ;;
    -db)
      db=$2
      shift 2
      ;;
    -p)
      pwd=$2
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

  if [ -z "$pwd" ]; then
      echo "Data pwd Cannot be empty"
      exit 1
  fi

  if [ -z "$db" ]; then
      echo "Data db Name Cannot be empty"
      exit 1
  fi

  if [ -z "$username" ]; then
      echo "Data username Cannot be empty"
      exit 1
  fi

  if [ "${port}" = 0 ]; then
      echo "Data port Name Cannot be empty"
      exit 1
  fi
}

#更改仓库密码
update_password(){
  echo "update data password ${pwd}"
  output=$( ${dir}/bin/psql -p ${port} -d postgres -c "ALTER USER ${username} WITH PASSWORD '${pwd}'")
  if [ $? -eq 0 ]; then
    echo "$output"
  else
    echo "update password Failed"
    exit 1
  fi
}


create_user(){
  echo "create user ${username}"
  output=$(${dir}/bin/createuser -p ${port} -s postgres)
}


#创建数据库
create_database(){
  echo "create database ${db}"
  output=$(${dir}/bin/psql -p ${port} -d postgres  -c "CREATE DATABASE ${db};")
  if [ $? -eq 0 ]; then
    echo "$output"
  else
    echo "update password Failed"
    exit 1
  fi
}

update(){
  create_user
  update_password
  create_database
}

update



