package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.Utils.ManageAccountUtil;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.Impl.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/manageAccount")
public class AdminAccountController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private UploadFileServiceImpl uploadFileService;
    @Autowired
    private CacheClient cacheClient;

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
    //后期需要更新
    @PostMapping("/setBlackList")
    public Result setBlackList(@RequestParam String userMail){
        //现在实际数据库中
        Account aimAccount=accountService.findAccountByUserMail(userMail);
        if(aimAccount!=null&&aimAccount.isEnabled()){
            aimAccount.setEnabled(false);
            accountService.save(aimAccount);
            cacheClient.addAccountIntoBlackList(userMail);
            return Result.success("已成功将"+userMail+"用户拉入黑名单");
        }else if(aimAccount==null){
            return Result.fail("用户"+userMail+"不存在");
        }else {
            return Result.fail("用户"+userMail+"已被拉入黑名单");
        }
    }
    //后期需要更新
    @PostMapping("/releaseFromBlackList")
    public Result releaseFromBlackList(@RequestParam  long id){
        Account account= accountService.getUserById(id);
        account.setEnabled(true);
        cacheClient.deleteAccountFromBlackList(account.getUserMail());
        return Result.success("已成功将"+id+"用户从黑名单内释放");
    }


    @GetMapping("/getRegisterUserNum")
    public Result getRegisterUserNum(){
        return Result.success(accountService.findAll().size());
    }







}
