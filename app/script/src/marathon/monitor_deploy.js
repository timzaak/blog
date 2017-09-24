const config = require('./config');
const client = require('marathon-node')(config.url,config.option);

const cAdvisor = {
  id:'/monitor/cadvisor',
  instance: 1,
  constraints: [["hostname","UNIQUE"]],
  labels:['monitor'],
  container:{
    type: 'DOCKER',
    docker: {
      image: 'google/cadvisor',
      network:'bridge'
    }
  },
  ipAddress:{
    groups:['monitor']
  },
  args:[],
  portMappings:[
    {
      containerPort:8080,
      hostPort:0
    }
  ]
};

const grafana = {
  id: '/monitor/grafana',
  dependencise: '/monitor/influxdab',
  labels:['monitor'],
  instance: 1,
  container:{
    type:'DOCKER',
    docker:{
      image: 'grafana/grafana',
      network: 'bridge'
    }
  },
  ipAddress:{
    groups:['monitor']
  },
  portMappings:[
    {
      containerPort:3000,
      hostPort:0
    }
  ],
  args:[]
};

const influxdb = {
  id: '/monitor/influxdb',
  labels:['monitor'],
  dependencise: '/monitor/cadvisor',
  instance: 1,
  constraints:[[]],
  container:{
    type:'DOCKER',
    docker:{
      image:'influxdb/influxdb',
      network:'bridge'
    },
    volumes:{
      containerPath: "influxdb",
      mode:'RW',
      persistent:{
        type:'root',
        size: 2048,
        maxSize: 3072,
        constraints:[["path","LIKE","/"]]
      }
    }
  },
  env:{},
  residency:{
    taskLostBehavior:"WAIT_FOREVER"
  },
  ipAddress:{
    groups:['monitor']
  },
  portMappings:[
    {
      containerPort:8083,
      hostPort:0
    },
    {
      containerPort:8086,
      hostPort:0
    }
  ],
  args:[],
  upgradeStrategy:{
    minimumHealthCapacity:0,
    maximumOverCapacity:0
  }
};

//client.apps.create(cAdvisor);
//client.apps.create(influxdAb);
//client.apps.create(grafana);
