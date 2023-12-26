package com.example.ooadgroupproject.entity;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Entity
public class Item {
    @Id
    @NotNull
    private String name;
    @Setter
    @NotNull
    private Integer num;
    @Setter
    @NotNull
    private Double price;
    @Setter
    @NotNull
    private String description;
    @Setter
    private String imagePath;

    private Item(String itemsName, Integer itemNum, Double price, String description, String imagePath){
        this.name = itemsName;
        this.num=itemNum;
        this.price=price;
        this.description=description;
        this.imagePath=imagePath;
    }
    public Item() {

    }

    public static Item generateNewItems
            (String itemsName,Integer itemNum,Double price,String description,String imagePath)throws Exception {
        String strPrice = price + "";
        if (strPrice.contains(".")) {
            //检查小数点后是否仅有最多两位小数
            if (strPrice.split("\\.")[1].length() > 2) {
                throw  new Exception("价格最多两位小数！ ");
            }
        }
        if(itemNum<0){
            throw new Exception("商品数量不能为负数！");
        }
        return new Item(itemsName,itemNum,price,description,imagePath);
    }
    public static Item generateNewItems
            (String JsonString)throws Exception {
        Item item=new Item();
        JSONObject jsonObject=new JSONObject(JsonString);
        item.num= jsonObject.getInt("num");
        item.name=jsonObject.getStr("name");
        item.price=jsonObject.getDouble("price");
        item.description=jsonObject.getStr("description");
        item.imagePath=jsonObject.getStr("imagePath");
        return item;
    }
    public boolean shoppingItem(Integer num){
        if(num>this.num){
            return false;
        }
        this.num-=num;
        return true;
    }
    public void addNum(Integer num){
        this.num+=num;
    }
    public Double calculateTotalPrice(Integer num){
        return this.price*num;
    }



}
