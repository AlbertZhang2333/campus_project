<template>
  <div>
    <el-row>
      <el-page-header class="back_info_style" >返回</el-page-header>
      <el-avatar class="user_info"  :size="80" :src="userAvatar" >
      </el-avatar>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        userAvatar:"https://img0.baidu.com/it/u=3276356649,3796473331&fm=253&fmt=auto&app=138&f=JPEG?w=500&h=500"
      }
    }
  }

</script>


<style>
  .back_info_style{
    font-size: 32px;
    margin-left: auto;
  }
  .user_info{
    position: absolute;
    top:3%;
    right: 5%;
  }


</style>
