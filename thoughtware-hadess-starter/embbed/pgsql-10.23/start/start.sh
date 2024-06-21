
# 默认的目录和应用参数值
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

  if [ -z "$data" ]; then
      echo "Data database Cannot be empty"
      exit 1
  fi
}

#启动
start_postgres(){
   echo "start postgres data:${data}"
   nohup ${dir}/bin/pg_ctl start -D ${data} >${dir}/log.log 2>&1 &
}

start(){
  valid_parameters
  start_postgres
}

start

























