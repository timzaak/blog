#!/bin/bash
cd server
./bin/backend -J-server -Duser.timezone=UTC -Dlogback.configurationFile=logback.pro.xml
