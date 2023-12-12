<template>
  <el-container style="height: 100%; border: 1px solid #eee">
    <el-aside width="aside_width" style="background-color: rgb(238, 241, 246)">
      <Aside :isCollapse="isCollapse" :width="aside_width"></Aside>
    </el-aside>

    <el-container>
      <el-header style="text-align: right; font-size: 12px; border-bottom: #dcdbdb 1px solid">
        <Header @doCollapse="doCollapse" :icon="icon"></Header>
      </el-header>

      <el-main>
        <Main></Main>
      </el-main>
    </el-container>
  </el-container>
</template>

<style>


.el-aside {
  color: #333;
}
</style>

<script>
import Aside from "./Aside";
import Header from "./Header";
import Main from "./Main";

export default {
  name: "Index",
  components: {Main, Header, Aside},
  data() {
    return {
      isCollapse: false,
      aside_width: '200px',
      icon: 'el-icon-s-fold'
    }
  },
  methods: {
    doCollapse() {
      // console.log(111)
      this.isCollapse = !this.isCollapse
      if (!this.isCollapse) {
        this.aside_width = '300px'
        this.icon = 'el-icon-s-fold'
      } else {
        this.aside_width = '64px'
        this.icon = 'el-icon-s-unfold'
      }
    }
  }
};
</script>