package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.Utils.ManageAccountUtil;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.Impl.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.HashMap;

@RestController
@RequestMapping("/manageAccount")
public class adminAccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @PostMapping("/batchAddAccount")
    public Result batchAddAccount(MultipartFile file) throws IOException {

        if(file==null || file.isEmpty()){
            return Result.fail("所选文件为空或还未选择文件");
        }
        String uploadResult=uploadFileService.uploadFile(file);
        Result result=ManageAccountUtil.batchAddAccount(uploadResult);
        if(result!=null){
            return result;
        }else {
            for(int i=0;i<ManageAccountUtil.accountList.size();i++){
                accountService.save(ManageAccountUtil.accountList.get(i));
            }
            return Result.success("已成功将表内的用户创建");
        }
    }
}
