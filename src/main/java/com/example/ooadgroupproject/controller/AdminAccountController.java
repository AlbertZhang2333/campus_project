package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.Utils.ManageAccountUtil;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.Impl.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@RestController
@RequestMapping("/manageAccount")
public class AdminAccountController {
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
    @GetMapping("/checkAllAccount")
    public List<Account> findAllAccount(){
        return accountService.findAll();
    }
    @PostMapping("/setBlackList")
    public Result setBlackList(long id){
       Account account= accountService.getUserById(id);
       account.setInBlackList(true);
       return Result.success("已成功将"+id+"用户拉入黑名单");
    }
    @PostMapping("/releaseFromBlackList")
    public Result releaseFromBlackList(long id){
        Account account= accountService.getUserById(id);
        if(account.isInBlackList()){
            account.setInBlackList(false);
            return Result.success("已成功将"+id+"用户从黑名单内释放");
        }else {
            return Result.fail("用户"+id+"不在黑名单内");
        }
    }


}
