package com.example.ooadgroupproject.controller;

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
    int num,@RequestParam double price,@RequestParam String description,@RequestParam String imagePath){
        try {
            return itemsService.generateANewItem(name, num, price, description, imagePath);
        }catch (Exception e){
            return Result.fail("输入的数据中存在不合规的数据");
        }
    }
    @PutMapping("/updateItem")
    public Result updateItem(@RequestParam String itemName,@RequestParam double price,
                             @RequestParam String description,@RequestParam String imagePath){
        return itemsService.updateItem(itemName,price,description,imagePath);
    }

}
