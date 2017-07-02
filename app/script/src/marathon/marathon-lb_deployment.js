const config = require('./config');
const client = require('marathon-lb')(config.url,config.option);
/*
  基本上要限定死hostname ["hostname","cluster","x.x.x.x"]
  ref: https://lsword.github.io/2016/08/10.html
 */
client.apps.create({
  id:'marathon-lb',
  instance:1,
  constraints:[["hostname","UNIQUE"]],
  container:{
    type:'DOCOKER',
    docker:{
      image:'docker.io/mesophere/marathon-lb',
      privileged:true,
      network:'host'
    }
  },
  args:['sse','-m','http://mesos:8080','-group','external']
})
