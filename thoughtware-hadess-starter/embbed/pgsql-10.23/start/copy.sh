
dir=""
file=""

#解析参数
echo "Parse startup parameters"
for arg in "$@"; do
  case $arg in
    -d)
      dir=$2
      shift 2
      ;;
     -f)
      file=$2
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
  if [ -z "${file}" ]; then
    echo "TarFile file Cannot be empty"
    exit 1
  fi
}

#解压文件
tar_xvf(){
  echo "tar ${file} --> ${dir}"
  tar -xvf "${file}" -C "${dir}"
}


copy(){
  valid_parameters
  tar_xvf
}

copy