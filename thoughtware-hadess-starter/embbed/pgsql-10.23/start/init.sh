
dir=""
data=""

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
}

#初始化仓库
init_db(){
  echo "init data ${data}"
  output=$(${dir}/bin/pg_ctl init -D ${data})
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
}

#复制文件
copy_file(){
   echo "copy postgres config!"
   cp "${dir}/conf/pg_hba.conf" "${data}"
   cp "${dir}/conf/postgresql.conf" "${data}"
}

valid_parameters

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