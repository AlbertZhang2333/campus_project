package com.example.ooadgroupproject.controller.Manager;

import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.Utils.ManageAccountUtil;
import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.common.SplitPage;
import com.example.ooadgroupproject.dao.AccountRepository;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.CacheClient;
import com.example.ooadgroupproject.service.Impl.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.ArrayList;
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
        String path="AddAccount";
        String uploadResult=uploadFileService.uploadFile(file,path);
        ArrayList<Account> result=ManageAccountUtil.batchAddAccount(uploadResult);
        if(result==null){
            return Result.fail("文件格式错误");
        }else {
            for(int i=0;i<result.size();i++){
                Account account=accountService.save(result.get(i));
            }
            return Result.success("已成功将表内的用户创建");
        }
    }
    @PostMapping("/batchSetAccountToBlackList")
    public Result batchSetAccountToBlackList(MultipartFile file)throws IOException{
        if(file==null||file.isEmpty()){
            return Result.fail("所选文件为空或还未选择文件");
        }
        String path="SetAccountToBlackList";
        String uploadResult=uploadFileService.uploadFile(file,path);
        ArrayList<String>result=ManageAccountUtil.batchSetAccountToBlacklist(uploadResult);
        int count=0;
        if(result==null){
            return Result.fail("文件格式错误");
        }else {
            for(int i=0;i<result.size();i++){
                Account account=accountService.findAccountByUserMail(result.get(i));
                if(account!=null) {
                    count++;
                    account.setEnabled(false);
                    accountService.save(account);
                    cacheClient.addAccountIntoBlackList(result.get(i));
                }
            }

        }
        return Result.success("已成功将"+count+"名用户拉入黑名单！");

    }
    @GetMapping("/checkAllAccount")
    public Result findAllAccount(@RequestParam int pageSize,@RequestParam int currentPage){
        List<Account>accountList=accountService.findAll();
        Long tot=(long)accountList.size();
        return Result.success(tot, SplitPage.getPage(accountList,pageSize,currentPage));
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
    public Result releaseFromBlackList(@RequestParam  String userMail){
        Account account= accountService.findAccountByUserMail(userMail);
        account.setEnabled(true);
        accountService.save(account);
        cacheClient.deleteAccountFromBlackList(account.getUserMail());
        return Result.success("已成功将"+userMail+"用户从黑名单内释放");
    }


    @GetMapping("/getRegisterUserNum")
    public Result getRegisterUserNum(){
        return Result.success(accountService.findAll().size());
    }


    @GetMapping("/findUserByUserMail")
    public Result findUserByUserMail(@RequestParam String userMail){
        if(userMail==null|| userMail.isEmpty()){
            return Result.fail("邮箱为空");
        }else {
            Account account=accountService.findAccountByUserMail(userMail);
            if(account==null){
                return Result.fail("该邮箱不存在");
            }else {
                return Result.success(account);
            }
        }
    }
    @GetMapping("/deleteUserByUserMail")
    public Result deleteUserByUserMail(@RequestParam String userMail){
        if(userMail==null|| userMail.isEmpty()){
            return Result.fail("邮箱为空");
        }else {
            Account account=accountService.findAccountByUserMail(userMail);
            if(account==null){
                return Result.fail("该邮箱不存在");
            }else {
                accountService.deleteById(account.getId());
                return Result.success("已成功删除该用户");
            }
        }
    }
//    @PostMapping("/register")
//    public Result addNewAccount(@RequestParam String username,
//                                @RequestParam String userMail,
//                                @RequestParam String password){
//        Account account=new Account(username,userMail,password,IdentityLevel.NORMAL_USER);
//        Account res=accountService.save(account);
//        if(res!=null) {
//            return Result.success("用户创建成功，欢迎您！" + username);
//        }else {
//            return Result.fail("邮箱重复！，注册失败");
//        }
//    }
    @PostMapping("/createANewAccount")
    public Result createANewAccount(@RequestParam int UserIcon,@RequestParam String userName,@RequestParam String userMail, @RequestParam String password,
                                    @RequestParam int identity){
        if(identity!=IdentityLevel.NORMAL_USER&&identity!=IdentityLevel.ACCOUNT_ADMIN){
            return Result.fail("身份指定错误");
        }
        Account account=new Account(UserIcon,userName,userMail,password,identity);
        Account res=accountService.save(account);
        if(res!=null) {
            return Result.success("用户创建成功" + userName);
        }else {
            return Result.fail("邮箱重复！，注册失败");
        }
    }

    @PostMapping("/setUserIcon")
    public Result setUserIcon(@RequestParam int UserIcon){
        Account account= LoginUserInfo.getAccount();
        if(account==null||account.getIdentity()==IdentityLevel.VISITOR){
            return Result.fail("用户未登录");
        }
        if(account==null){
            return Result.fail("用户未登录");
        }else {
            account.setUserIcon(UserIcon);
            accountService.save(account);
            return Result.success("已成功将用户的头像设置为"+UserIcon);
        }
    }






}
