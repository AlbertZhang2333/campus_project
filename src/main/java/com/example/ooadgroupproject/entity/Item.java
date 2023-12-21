package com.example.ooadgroupproject.entity;

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
    private int num;
    @Setter
    @NotNull
    private double price;
    @Setter
    @NotNull
    private String description;
    @Setter
    private String imagePath;

    private Item(String itemsName, int itemNum, double price, String description, String imagePath){
        this.name = itemsName;
        this.num=itemNum;
        this.price=price;
        this.description=description;
        this.imagePath=imagePath;
    }
    public Item() {

    }

    public Item generateNewItems
            (String itemsName,int itemNum,double price,String description,String imagePath)throws Exception {
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
    public boolean shoppingItem(int num){
        if(num>this.num){
            return false;
        }
        this.num-=num;
        return true;
    }
    public void addNum(int num){
        this.num+=num;
    }
    public double calculateTotalPrice(int num){
        return this.price*num;
    }



}
