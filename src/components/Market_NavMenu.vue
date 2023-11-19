<template>
  <div>
    <el-row>
      <el-col :span="12">
        <el-menu
          default-active="2"
          class="el-menu-vertical-demo"
          @open="handleOpen"
          @close="handleClose">
          <el-submenu index="1">
            <template slot="title">
              <i class="el-icon-location"></i>
              <span>管理员入口</span>
            </template>
              <el-menu-item index="1-1">选项1</el-menu-item>
              <el-menu-item index="1-2">选项2</el-menu-item>
          </el-submenu>
          <el-menu-item index="2" @click="openShoppingCartDialog">
            <i class="el-icon-s-goods"></i>
            <span slot="title">购物车</span>
          </el-menu-item>
          <el-menu-item index="3" @click="feedbackCollectionDialog">
            <i class="el-icon-setting"></i>
            <span slot="title">意见收集</span>
          </el-menu-item>
        </el-menu>
      </el-col>
    </el-row>
    <el-dialog :visible.sync="shoppingCart_dialog">
      <el-table
        :data="MarketShoppingCartInfo"
        stripe
        style="width: 100%">
        <el-table-column
          prop="add date"
          label="商品加入购物车日期"
          width="180">
        </el-table-column>
        <el-table-column
          prop="item name"
          label="商品名称"
          width="180">
        </el-table-column>
        <el-table-column
          prop="shopping num"
          label="购买数量">
        </el-table-column>
        <el-table-column
          prop="amount price"
          label="预计花费¥">
        </el-table-column>
        <el-table-column>
          <template #default="scope">
            <el-button style="margin-left: auto" @click="deleteShoppingCartItem(scope.$index)">移除此商品</el-button>
            <el-button style="margin-left: auto">支付此商品</el-button>
          </template>
        </el-table-column>

        <el-table-row>
          <span>通通拿下</span>
        </el-table-row>
      </el-table>
    </el-dialog>

    <el-dialog :visible.sync="feedback_dialog">
      <el-input v-model="feedbackContent" aria-placeholder="您的意见是对我们最大的帮助！"></el-input>
    </el-dialog>
  </div>
<!--    <el-dialog>-->
<!--  </el-dialog>-->
</template>

<script>
import Sustech_Market from "./Sustech_Market.vue";
export default {
  props:['MarketShoppingCartInfo'],
  methods: {
    handleOpen(key, keyPath) {
      console.log(key, keyPath);
    },
    handleClose(key, keyPath) {
      console.log(key, keyPath);
    },
    openShoppingCartDialog() {
      this.shoppingCart_dialog=true;
      console.log(this.MarketShoppingCartInfo[0])
    },
    deleteShoppingCartItem(index){
      this.MarketShoppingCartInfo.splice(index,1);
      console.log(index);
    },
    feedbackCollectionDialog(){
      this.feedback_dialog=true;
    }
  },
  data(){
    return{
      shoppingCart_dialog:false,
      feedback_dialog:false,
      feedbackContent:""
    }

  }
}

</script>



<style>
.el-menu-vertical-demo {
  width: 120%;
  height: 150%;
  font-size: 20px;
  border-radius: 4px;
  margin:auto;
  position: absolute;

}

</style>
