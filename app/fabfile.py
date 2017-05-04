#!/usr/bin/env python
# -*- encoding: utf-8 -*-
from fabric.api import *
from datetime import  *
env.hosts="timzaak"

@runs_once
def make_jar():
    local('sbt universal:packageBin')
@runs_once
def target_path():
     return prompt('please input directory:',default='~/100person_backend/server')
@task
def scp():
    make_jar()
    getdirname = target_path()
    confirm('is this path ? '.getdirname)
    #将本地的打包zip上传到远程服务器
    put('target/universal/*.zip', getdirname)
    cd(getdirname)
    run('unzip backend-0.1.0-SNAPSHOT.zip&&rm backend-0.1.0-SNAPSHOT.zip')
    newPath = datetime.now().strftime("%Y-%m-%d_%H_%M")
    run('mv backend-0.1.0-SNAPSHOT ' + newPath)
    #cd(newPath)
    #run('./bin/backend  -Dlogback.configurationFile=logback.pro.xml &')

@task
def deploy():
    scp()

    #....
