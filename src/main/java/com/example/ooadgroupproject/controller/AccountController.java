package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ooadgroupproject.IdentityLevel;

@RestController
@RequestMapping("/login")

public class AccountController {
    @Autowired
    //jpa会自动依照规范的命名确定出所需求的简单功能，并直接将之实现和实例化
    private AccountService accountService;//=new AccountServiceImpl();new部分可写可不写

    @Autowired

    JwtUtils jwtUtils;
    @Autowired private EmailService emailService;

    @PostMapping("/register")
    public Result addNewAccount(@RequestParam String username,
                                 @RequestParam String userMail,
                                 @RequestParam String password){
        Account account=new Account(username,userMail,password,IdentityLevel.NORMAL_USER);
        Account res=accountService.save(account);
        if(res!=null) {
            return Result.success("用户创建成功，欢迎您！" + username);
        }else {
            return Result.fail("邮箱重复！，注册失败");
        }
    }

//    @PutMapping("/ModifyingInformation")
//    public Result update (@RequestParam long id,
//                           @RequestParam String username,
//                           @RequestParam String password){
//        Account account=new Account();
//        account.setPassword(password);
//        account.setUsername(username);
//        accountService.save(account);
//        return Result.success("账号信息修改成功！");
//    }
//    @DeleteMapping("/{id}")
//    public void deleteOne(@PathVariable long id){
//        accountService.deleteById(id);
//    }

    //登录：
    @Deprecated
    @PostMapping("/login/loginCheck")
    public Result login(@RequestParam String userMail,
                        @RequestParam String password){
        Account account=accountService.AccountExistCheck(userMail,password);
        if(account!=null){
            String token=jwtUtils.generateToken(userMail,account.getUsername(),account.getIdentity());
            return Result.success(token);
        }else {
            return Result.fail("账号或密码错误");
        }
    }


    //和前端配合，让用户页面进行跳转到登录用户界面


    @PostMapping("/forgetPassword")
    public Result sendVerificationMail(@RequestParam String userMail){
        if(accountService.findAccountByUserMail(userMail)==null)return Result.fail("不存在该账户");
        return emailService.sendVerifyCodeEmail(userMail);
    }
    @PostMapping("/verificationEmail")
    public String verifyMailAndChangePassword(String code,String userMail,String password) {
        if (emailService.verifyCode(code)) {
            Account account = accountService.findAccountByUserMail(userMail);
            account.setPassword(password);
            accountService.save(account);
            return password;
        } else {
            throw new Error("邮箱验证码输入错误");
        }
    }
}
