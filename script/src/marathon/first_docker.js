const fetch = require('node-fetch');

const url = 'http://mesos:8080/';
const opts = {};
const client = require('marathon-node')(url,opts);


const firstDocker = {
  "id":"hello-world1",
  "cmd":"top",
  "cpus":0.2,
  "mem":64.0,
  "container": {
    "type": "DOCKER",
    "docker": {
      "network": "BRIDGE",
      "image": "hello-world",
     /* "portMappings":[
        {
          "containerPort":8080,
          hostPort:0,
          servicePort:9000,
          protocol:"tcp"
        }
      ]*/
    }
  }
 /* Healthchecks:[
    {
      protocal:"HTTP",
      portIndex:0,
      path:"/",
      "gracePeriodSeconds":5,
      intervalSeconds:30,
      maxConsecutiveFailures:3
    }
  ]*/
};

//client.apps.create(firstDocker);
//client.app.getList().then(console.log).catch(console.err)

const testMarathon = {
  id:"basic",
  cmd : "while[ true ]; do echo 'Hello World'; sleep5;done",
  cpu : 0.1,
  mem :10,
  instances : 1
};

//client.apps.create(testMarathon);
//client.info.get().then(console.log).catch(console.err);

const basic_3 = {
  id:"basic-3",
  cmd:"python -m http.server 8080",
  cups:0.5,
  mem:32,
  container:{
    type:"DOCKER",
    docker:{
      image:"python:3",
      network:"BRIDGE",
      portMappings:[{
        containerPort:8080,
        hostPort:0
      }]
    }
  }
}

client.apps.create(basic_3);
