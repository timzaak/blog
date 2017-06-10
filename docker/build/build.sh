#!/bin/bash
server="backen-1.0"
base=$(pwd)
echo "Notice::========================="
echo "Please keep the git repo is clean"
echo "We do nothing for repo is not clean"
if [ ! -d "../../app" ] && [ ! -d "../../very-util-lang" ]; then
  echo "please check your files. git submodule init ..."
  echo "Error....."
  exit -1
fi
git fetch
echo "[OK] git fetch"
if [ $# -gt 0 ]; then
  for patch in "$@"
  do
    echo "====== go to docker the branch $path"
    git checkout $patch
    git pull
    tag="$(patch)_$(date +%y%m%d%H%M)"
  done
else
  patch=$(git symbolic-ref --short -q HEAD)
  echo "=========go to docker the branch $patch"
  tag="${patch}_$(date +%y%m%d%H%M)"
fi

echo "your tag is: $tag"

cd ../../app
sbt clean universal:packageBin


cp ../../app/target/universal/$server.zip $base

unzip $server.zip
rm -fr $server.zip

cd $server
tag="test"
echo "===========build docker image.."
docker build -t="backend_server:$tag" $base
