package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.dao.ItemsRepository;
import com.example.ooadgroupproject.entity.Item;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.ItemsService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class ItemServiceImpl implements ItemsService {
    @Autowired
    private ItemsRepository itemsRepository;
    @Autowired
    private CacheClient cacheClient;
    @Override
    public List<Item> findAll(){
        return itemsRepository.findAll();
    }
    private final Logger logger=Logger.getLogger(ItemServiceImpl.class);
    @Override
    public Item findByName(String name) {
        //说明该商品最近被人查询过，存在被多次查询的可能，现在对其添加缓存
        String itemJSON=cacheClient.getItemInfo(name);
        Item item=null;
        if(itemJSON!=null){
            if(!itemJSON.isEmpty()){
                try {
                    item = Item.generateNewItems(itemJSON);
                    return item;
                }catch (Exception e){
                    return null;
                }
            }else {
                return null;
            }
        }
        //需要想个办法确保和验证item为最新版
        item=itemsRepository.findByName(name).orElse(null);
        cacheClient.setItemInfo(name,item);
        return item;
    }
    @Override
    public Result updateItem(String itemName, Double price,
                             String description, String imagePath){
        Item item=itemsRepository.findByName(itemName).orElse(null);
        if(item==null){
            return Result.fail("不存在该商品");
        }
        item.setPrice(price);
        item.setDescription(description);
        item.setImagePath(imagePath);
        itemsRepository.save(item);
        cacheClient.deleteItems(item);
        return Result.success("商品数据已修改");
    }



    @Override
    public boolean addItem(String name, Integer addNum) {
        Item item = itemsRepository.findByName(name).orElse(null);
        if (item != null) {
            item.addNum(addNum);
            cacheClient.setItemInfo(item.getName(),item);
            itemsRepository.save(item);
        }
        return false;
    }

    //此方法仅仅由销售记录service调用，不得由controller调用
    @Override
    public boolean reduceItem(String name, Integer num) {
        Item item = findByName(name);
        if(item == null){
            return false;
        }
        boolean res=item.shoppingItem(num);
        if(res) {
            cacheClient.setItemInfo(item.getName(),item);
            itemsRepository.save(item);
        }
        return res;
    }

    @Override
    public Double calculatePrice(Item item, Integer num) {
        return item.calculateTotalPrice(num);
    }

    @Override
    public Result generateANewItem(String name, Integer num, Double price, String description, String imagePath) throws Exception {
        if(itemsRepository.findByName(name).isPresent()){
            return Result.fail("该名称的商品已存在");
        }
        Item item=Item.generateNewItems(name,num,price,description,imagePath);
        itemsRepository.save(item);
        return Result.success("目标商品已创建");
    }

    @Override
    public Result deleteItem(String name){
        Item item=itemsRepository.findByName(name).orElse(null);
        if(item==null){
            return Result.fail("不存在该商品");
        }
        itemsRepository.delete(item);
        cacheClient.deleteItems(item);
        return Result.success("商品已删除");
    }

    @Override
    public Result setInstantItem(String itemName, int num, long days){
        if(!reduceItem(String.valueOf(itemName),num)){
            return Result.fail("商品不存在或库存不足！");
        }
        Item item=findByName(itemName);
        item.setNum(num);
        cacheClient.setInstantItem(item,days, TimeUnit.DAYS);
        return Result.success("商品已设置");
    }
    @Override
    public Result getInstantItem() throws Exception {
        List<String>itemList=cacheClient.getInstantItem();
        if(itemList==null||itemList.isEmpty()){
            return Result.success(new ArrayList<Item>());
        }
        ArrayList<Item>items=new ArrayList<>();
        for(int i=0;i<itemList.size();i++){
            try {
                if (itemList.get(i) != null && !itemList.get(i).isEmpty()) {
                    items.add(Item.generateNewItems(itemList.get(i)));
                }
            }catch (Exception e){
                logger.error("存在秒杀商品读取异常:"+e.getMessage());
            }
        }
        return Result.success(items);
    }
    @Override
    public Item getInstantItem(String itemName){
        String itemJson=cacheClient.getInstantItem(itemName);
        if(itemJson!=null){
            try {
                return Item.generateNewItems(itemJson);
            }catch (Exception e){
                return null;
            }
        }else {
            return null;
        }
    }
    @Override
    public boolean deleteInstantItem(String itemName){
        return cacheClient.deleteInstantItem(itemName);
    }





}
