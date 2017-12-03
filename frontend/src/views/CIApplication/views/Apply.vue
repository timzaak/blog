<template lang="jade">
  div
    el-row(style="margin-top:10px")
      el-col(:offset="1", span="8")
        el-input(placeholder="请输入 branch 名称")
          tempate(slot="prepend") CI-
      el-col(:offset="1",:span="4")
         el-button(type="primary") Apply
    el-row(style="margin-top:6px")
      el-col(:offset="1",span="10")
        | 当前还余&nbsp;
        span(style="color:red") 3
        | &nbsp;套环境

</template>
<style lang="css" scoped>

</style>
<script>

  export default{

  }
</script>
