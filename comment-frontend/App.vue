<template>
  <div id="app">
    <!--    <img alt="Vue logo" src="./assets/1.jpg">-->
    <Index msg="第一个页面"/>
  </div>
</template>

<script>
import Index from './comments/Index.vue'

export default {
  name: 'App',
  components: {
    Index
  }
}
</script>

<style>
#app {
  height: 100%;
}
</style>
