
<template>
  <div>
    <Market_headline></Market_headline>
    <h2>
     商场管理界面
    </h2>
    <el-tabs v-model="activeName" type="card" @tab-click="handleClick">
      <el-tab-pane label="当前正销售商品" name="Current_sales_items">用户管理
        <el-row>
          <el-button @click="openAddItemDialog">
            增添商品
          </el-button>
        </el-row>
        <el-table
          :data="onSaleShoppingItemList"
          style="width: 100%"
          :row-class-name="tableRowClassName">
          <el-table-column
            prop="Item Id"
            label="商品编号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="Item Name"
            label="商品名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="price"
            label="商品定价"
            width="180">
          </el-table-column>
          <el-table-column
            prop="sales volume"
            label="商品销量">
          </el-table-column>
          <el-table-column>
            <template #default="scope">
              <el-button @click="editItem(scope.$index)">
                编辑
              </el-button>
              <el-button @click="deleteItem(scope.$index)">
                移除
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="销售记录" name="Transaction_records">销售记录
        <el-table
          :data="TransactionRecordsList"
          style="width: 100%"
          :row-class-name="tableRowClassName">
          <el-table-column
            prop="TransactionId"
            label="交易编号">
          </el-table-column>
          <el-table-column
            prop="PaymentTime"
            label="交易时间"
            width="180">
          </el-table-column>
          <el-table-column
            prop="ItemId"
            label="商品编号"
            width="180">
          </el-table-column>
          <el-table-column
            prop="ItemName"
            label="商品名称"
            width="180">
          </el-table-column>
          <el-table-column
            prop="total_amount"
            label="交易总金额"
            width="180">
          </el-table-column>
          <el-table-column
            prop="sales_volume"
            label="交易量">
          </el-table-column>
          <el-table-column
            prop="userId"
            label="购买用户id">
          </el-table-column>
          <el-table-column>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog :visible.sync="addItem_dialog" >
      <el-row style="margin: auto">新增商品</el-row>
      <el-form
        ref="onSaleShoppingItem"
        :model="onSaleShoppingItem"
        :rules="ItemAddRule"
        label-width="auto"
        label-position="right"
        size="default"
        style="margin-top: 3%"
      >
        <el-form-item label="商品编号" prop="ItemId">
          <el-input v-model="onSaleShoppingItem.ItemId"/>
        </el-form-item>
        <el-form-item label="商品照片路径" prop="picture_path">
          <el-input v-model="onSaleShoppingItem.picture_path"/>
        </el-form-item>
        <el-form-item label="商品名称" prop="ItemName">
          <el-input v-model="onSaleShoppingItem.ItemName"/>
        </el-form-item>
        <el-form-item label="商品售价(¥)" prop="price">
          <el-input v-model="onSaleShoppingItem.price"/>
        </el-form-item>
        </el-form>
      <el-button @click="submitAddItem(true,-1)" style="margin-left: 45%">
        提交商品
      </el-button>
    </el-dialog>

    <el-dialog :visible.sync="updateItem_dialog" >
      <el-row style="margin: auto">新增商品</el-row>
      <el-form
        ref="onSaleShoppingItem"
        :model="onSaleShoppingItem"
        :rules="ItemAddRule"
        label-width="auto"
        label-position="right"
        size="default"
        style="margin-top: 3%"
      >
        <el-form-item label="商品编号" prop="ItemId">
          <el-input v-model="onSaleShoppingItem.ItemId"/>
        </el-form-item>
        <el-form-item label="商品照片路径" prop="picture_path">
          <el-input v-model="onSaleShoppingItem.picture_path"/>
        </el-form-item>
        <el-form-item label="商品名称" prop="ItemName">
          <el-input v-model="onSaleShoppingItem.ItemName"/>
        </el-form-item>
        <el-form-item label="商品售价(¥)" prop="price">
          <el-input v-model="onSaleShoppingItem.price"/>
        </el-form-item>
      </el-form>
      <el-button @click="updateAddItem()" style="margin-left: 45%">
        修改商品
      </el-button>
    </el-dialog>
    </div>
</template>

<script>
import Market_headline from "./Market_headline.vue";
export default {
components:{
  Market_headline
},
  methods: {
    tableRowClassName({row, rowIndex}) {
      if (rowIndex === 1) {
        return 'warning-row';
      } else if (rowIndex === 3) {
        return 'success-row';
      }
      return '';
    },
    handleClick(tab, event) {
      console.log(tab, event);
    },
    openAddItemDialog(){
      this.addItem_dialog=true;
    },
    submitAddItem(submitOrUpdate,index){
      console.log(this.onSaleShoppingItemList);
      this.$refs.onSaleShoppingItem.validate((valid) =>{
        console.log(this.onSaleShoppingItem.ItemId);
        if(valid){

            this.onSaleShoppingItemList.push({
              "Item Id": this.onSaleShoppingItem.ItemId,
              "picture path": this.onSaleShoppingItem.picture_path,
              "Item Name": this.onSaleShoppingItem.ItemName,
              "price": this.onSaleShoppingItem.price,
              "sales volume": 0
            })
            console.log("tttttttt");
            alert('submit!');
            this.addItem_dialog=false;
            this.onSaleShoppingItem.ItemId="";
            this.onSaleShoppingItem.picture_path="";
            this.onSaleShoppingItem.ItemName="";
            this.onSaleShoppingItem.price=-1;
            this.onSaleShoppingItem.sales_volume=0;
        }
      })
    },
    editItem(index){
      this.updateItem_dialog=true;
      console.log("编辑商品表单");
      this.onSaleShoppingItem.price=this.onSaleShoppingItemList[index]["price"];
      this.onSaleShoppingItem.ItemName=this.onSaleShoppingItemList[index]["Item Name"];
      this.onSaleShoppingItem.ItemId=this.onSaleShoppingItemList[index]["Item Id"];
      this.onSaleShoppingItem.picture_path=this.onSaleShoppingItemList[index]["picture path"];
      this.onSaleShoppingItem.sales_volume=this.onSaleShoppingItemList[index]["sales volume"];
      this.editItemIndex=index;
      console.log(this.editItemIndex);
    },
    updateAddItem(){
      console.log(this.editItemIndex);
      console.log(this.onSaleShoppingItemList);
      console.log(this.onSaleShoppingItemList[this.editItemIndex]);
      this.onSaleShoppingItemList[this.editItemIndex]["Item Id"]=this.onSaleShoppingItem.ItemId;
      this.onSaleShoppingItemList[this.editItemIndex]["picture path"]=this.onSaleShoppingItem.picture_path;
      this.onSaleShoppingItemList[this.editItemIndex]["Item Name"]=this.onSaleShoppingItem.ItemName;
      this.onSaleShoppingItemList[this.editItemIndex]["price"]=this.onSaleShoppingItem.price;
      console.log(this.editItemIndex);
      alert('update!');
      this.editItemIndex=-1;
      this.onSaleShoppingItem.ItemId="";
      this.onSaleShoppingItem.picture_path="";
      this.onSaleShoppingItem.ItemName="";
      this.onSaleShoppingItem.price=-1;
      this.onSaleShoppingItem.sales_volume=0;
      this.updateItem_dialog=false;
    },
    deleteItem(index){
      this.onSaleShoppingItemList.splice(index,1);
    }
  },
  data() {
    const validateItemPrice=(rule,value,callback)=>{
      if(!value){
        return callback(new Error('This field must be filled in'));
      }else if(value<=0){
        return callback(new Error('这会让我们倒闭的'));
      }
      return callback();
    };
    const validateItemId=(rule,value,callback)=>{
      if(!value){
        return callback(new Error('This field must be filled in'));
      }else if(value<0){
        return callback(new Error('不合法的商品编号'));
      }
      return callback();
    };
    return {
      activeName: 'Current_sales_items',
      onSaleShoppingItem:{
        ItemId:-1,
        picture_path:"",
        ItemName:"",
        price:0,
        sales_volume:0
      },
      TransactionRecords:{
        TransactionId:-1,
        PaymentTime:new Date(),
        ItemId:-1,
        ItemName:"",
        total_amount:0,
        sales_volume:0,
        userId:-1
      },
      onSaleShoppingItemList:[{
        "Item Id":1,
        "picture path":"https://img1.baidu.com/it/u=3713914161,4224177338&fm=253&fmt=auto&app=120&f=JPEG?w=500&h=625",
        "Item Name":"温彻斯特",
        "price":42,
        "sales volume":0
      }],
      TransactionRecordsList:[{
        TransactionId:-1,
        PaymentTime:new Date(),
        ItemId:-1,
        ItemName:"",
        total_amount:0,
        sales_volume:0,
        userId:-1
      }],

      ItemAddRule:{
        ItemName:[
          {required:true,trigger:'blur'}
        ],
        ItemId:[
          {required:true,validator:validateItemId,trigger:'blur'}
        ],
        picture_path:[
          {required:true,trigger:'blur'}
        ],
        price:[
          {required:true,validator:validateItemPrice,trigger:'blur'}
        ],

      },

      addItem_dialog:false,
      updateItem_dialog:false,
      editItemIndex:-1
    }
  }
}
</script>

<style>
.el-table .warning-row {
  background: oldlace;
}

.el-table .success-row {
  background: #f0f9eb;
}
</style>
