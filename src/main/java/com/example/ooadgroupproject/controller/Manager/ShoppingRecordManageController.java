package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.service.ItemsShoppingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ManageShoppingRecord")
public class ShoppingRecordManageController {
    @Autowired
    private ItemsShoppingRecordService itemsShoppingRecordService;
    @RequestMapping("/findShoppingRecordByItemName")
    public String findShoppingRecordByItemName(String itemName){
        return itemsShoppingRecordService.findByItemName(itemName).toString();
    }
    @RequestMapping("/findShoppingRecordByUserMail")
    public String findShoppingRecordByUserMail(String userMail){
        return itemsShoppingRecordService.findByUserMail(userMail).toString();
    }


}
