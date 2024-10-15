#!/bin/bash

dir=""
dbAddress=""

#解析参数
echo "Parse startup parameters"
for arg in "$@"; do
  case $arg in
    -d)
      dir=$2
      shift 2
      ;;
    -D)
      dbAddress=$2
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
  if [ -z "${dbAddress}" ]; then
      echo "Data version Name Cannot be empty"
      exit 1
  fi
}

remove_file(){
    echo "delete file ${dbAddress}/*"
    rm -rf ${dbAddress}/pg_hba.conf
    rm -rf ${dbAddress}/postgresql.conf
}

cp_file(){
  echo "cp file ${dir}/conf/*"
  cp ${dir}/conf/pg_hba.conf ${dbAddress}/
  cp ${dir}/conf/postgresql.conf ${dbAddress}/
}

copy(){
  valid_parameters
  remove_file
  cp_file
}

copy

