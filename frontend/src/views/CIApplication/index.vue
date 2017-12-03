<template lang="jade">

  el-row
    el-col(span="5")
      el-menu(:default-active="activeIndex" mode="vertical",background-color="#545c64", text-color="#fff",active-text-color="#ffd04b")
        el-menu-item(index="1") Apply
        el-menu-item(index="2") Waiting
    el-col(:span="19")
      router-view

</template>
<style lang="css" scoped>

</style>
<script>
  export default {
    data(){
      return {
        activeIndex: "1"
      }
    },

  }


</script>
