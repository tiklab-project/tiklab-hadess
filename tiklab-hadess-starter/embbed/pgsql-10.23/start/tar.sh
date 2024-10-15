#!/bin/bash

dir=""
version=""

#解析参数
echo "Parse startup parameters"
for arg in "$@"; do
  case $arg in
    -d)
      dir=$2
      shift 2
      ;;
    -v)
      version=$2
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
  if [ -z "${version}" ]; then
      echo "Data version Name Cannot be empty"
      exit 1
  fi
}

#解压文件
tar_xvf(){
  echo "tar file ${dir}/${version}.tar.gz"
  echo "tar -xvf ${dir}/${version}/${version}.tar.gz -C /${dir}"
  tar -xvf ${dir}/${version}/${version}.tar.gz -C ${dir}
}

copy(){
  valid_parameters
  tar_xvf
}

copy