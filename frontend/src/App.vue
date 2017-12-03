<template>
  <div>
    <el-row id="navigator">
      <el-col :offset="5" :span="19">
        <el-menu :default-active="activeIndex" mode="horizontal" @select="handleSelect">
          <el-menu-item index="1"> 环境申请 </el-menu-item>
          <el-menu-item index="2"> Jenkins </el-menu-item>
          <el-menu-item index="3"> Mesos </el-menu-item>
          <el-menu-item index="4"> Marathon </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
    <router-view></router-view>
  </div>
</template>

<script>
  import config from './config'
  function go(url) {
    window.open(url)
  }
  export default {
    data(){
      return {
        activeIndex: "1",
      }
    },
    methods: {
      handleSelect(key){
        switch (key) {
          case '1':
            break
          case '2':
            go(config.jenkins_host)
            break
          case '3':
            go(config.mesos_host)
            break
          case '4':
            go(config.marathon_host)
            break;
        }
      }
    }
  }
</script>
