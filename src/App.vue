<template>
  <div>
<!--    <Administrators_Market></Administrators_Market>-->
    <el-header>
      <Market_headline/>
    </el-header>
    <el-container>
      <el-aside>
        <Market_NavMenu
        :MarketShoppingCartInfo="this.MarketShoppingCartInfo"/>
      </el-aside>
      <el-main>
        <Sustech_Market @Market_sendShoppingCartInfo="getMarketShoppingCart"/>
      </el-main>
    </el-container>

  </div>
</template>

<script>
import Market_NavMenu from "./components/Market_NavMenu.vue";
import Sustech_Market from "./components/Sustech_Market.vue";
import Market_headline from "./components/Market_headline.vue";
import Administrators_Market from "./components/Administrators_Market.vue";


export default {
    data() {
        return {
            MarketShoppingCartInfo: [],
          deleteCartItem_index:-1
        }
    },
  components: {
    Market_headline,
    Sustech_Market,
    Market_NavMenu,
    Administrators_Market
  },
    methods:{
      // 拿取Sustech_Market中的新加入购物车的购物信息
        getMarketShoppingCart(infos){
            this.MarketShoppingCartInfo=infos;
            console.log(infos);
        },

    }
}
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 5%;
}
.background_Picture{
  background: url("https://img1.baidu.com/it/u=1555536650,780667891&fm=253&fmt=auto&app=138&f=JPEG?w=800&h=500")
  no-repeat;
  position: absolute;


}

</style>
