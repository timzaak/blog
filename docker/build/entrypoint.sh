#!/bin/bash
set -e
cd server
echo "begin to runing server....."
./bin/backend -J-server -Duser.timezone=UTC -Dlogback.configurationFile=logback.pro.xml
