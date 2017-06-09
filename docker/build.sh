#!/bin/bash
base=$(pwd)
if [! -d "app"] && [! -d "very-util-lang"]; then
  echo "please check your files. git submodule init ..."
  exit -1
fi
git fetch

if [$# -gt 0]; then
  for patch in "$@"
  do
    echo "====== go to docker the branch $path"
    git checkout $patch
    git pull
    tag="${patch}_${date +%y%m%d%H%M}"
  done
else
  path=${git symbolic-ref --short -q HEAD}
  echo "=========go to docker the branch $path"
  tag="${patch}_${date +%y%m%d%H%M}"
fi

cd ../app
sbt clean
sbt universal:packageBin
cp target/universal/*.zip $base/app.zip

cd $base
unzip app.zip
echo "===========build docker image.."
docker build -t app_server .
