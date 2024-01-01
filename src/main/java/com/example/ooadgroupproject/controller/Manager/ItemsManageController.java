package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ManageItems")
public class ItemsManageController {
    @Autowired
    ItemsService itemsService;


    @GetMapping("/findAll")
    public Result findAll(){
        return Result.success(itemsService.findAll());
    }

    @GetMapping("/findByName")
    public Result findByName(@RequestParam  String name){
       Item item = itemsService.findByName(name);
       if(item==null){
           return Result.fail("不存在该商品");
       }else return Result.success(item);
    }

    @PostMapping("/generateANewItem")
    public Result generateANewItem(@RequestParam String name,@RequestParam
    Integer num,@RequestParam Double price,@RequestParam String description,@RequestParam String imagePath){
        try {
            return itemsService.generateANewItem(name, num, price, description, imagePath);
        }catch (Exception e){
            return Result.fail("输入的数据中存在不合规的数据");
        }
    }

    @PutMapping("/updateItem")
    public Result updateItem(@RequestParam String itemName,@RequestParam Double price,
                             @RequestParam String description,@RequestParam String imagePath){
        return Result.success(itemsService.updateItem(itemName,price,description,imagePath));
    }

    @DeleteMapping("/deleteItem")
    public Result deleteItem(@RequestParam String name){
        return Result.success(itemsService.deleteItem(name));
    }

    @PostMapping("/addInstantItem")
    public Result addInstantItem(@RequestParam String itemName,@RequestParam int num,@RequestParam long shoppingDays){
        itemsService.setInstantItem(itemName,num,shoppingDays);
        return Result.success("商品已添加！");
    }
    @PostMapping("/deleteInstantItem")
    public Result deleteInstantItem(@RequestParam String itemName){
        Item item=itemsService.findByName(itemName);
        if(item==null){
            return Result.fail("不存在该商品");
        }
        Item item1=itemsService.getInstantItem(itemName);
        if(item1==null){
            return Result.fail("该商品不是秒杀商品");
        }
        boolean t=itemsService.deleteInstantItem(itemName);
        item.setNum(item1.getNum()+item.getNum());
        return Result.success("已成功取消秒杀活动");
    }

}
