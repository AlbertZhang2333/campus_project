<script>
export default {
  name: "Aside",
  data() {
    return {}
  },
  props: {
    isCollapse: Boolean,
    width: String
  }
}
</script>

<template>
  <el-menu style="height: 100%" background-color="#545c64"
           text-color="#fff"
           active-text-color="#ffd04b"
           default-active="/Home"
           :collapse="isCollapse"
           :collapse-transition=false
  >
    <el-menu-item index="/Home" :width="width">
      <i class="el-icon-s-home"></i>
      <span slot="title" style="text-align: center">首页</span>
    </el-menu-item>
    <el-menu-item index="/One" :width="width">
      <i class="el-icon-s-flag"></i>
      <span slot="title" style="text-align: center">1</span>
    </el-menu-item>
    <el-menu-item index="/Two" :width="width">
      <i class="el-icon-s-home"></i>
      <span slot="title" style="text-align: center">2</span>
    </el-menu-item>
  </el-menu>
</template>

<style scoped>

</style>