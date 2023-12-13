package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.Utils.JwtUtils;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.EmailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.CS_Attribute;
import java.util.List;

@RestController
@RequestMapping("/login")

public class AccountController {
    @Autowired
    //jpa会自动依照规范的命名确定出所需求的简单功能，并直接将之实现和实例化
    private AccountService accountService;//=new AccountServiceImpl();new部分可写可不写

    @Autowired
    JwtUtils jwtUtils;

    @PutMapping("/register")
    public Result addNewAccount(@RequestParam String username,
                                 @RequestParam String userMail,
                                 @RequestParam String password){
        Account account=new Account(username,userMail,password,IdentityLevel.NORMAL_USER);
        accountService.save(account);
        return Result.success("用户创建成功，欢迎您！"+username);
    }

    @PutMapping("/ModifyingInformation")
    public Account update (@RequestParam long id,
                           @RequestParam String username,
                           @RequestParam String password,
                           @RequestParam boolean inBlackList,
                           @RequestParam int identity){
        Account account=new Account();
        account.setId(id);//如果该id未存在于表内，将创建新的一条数据，否则为修改已有数据
        account.setIdentity(identity);
        account.setPassword(password);
        account.setUsername(username);
        account.setInBlackList(inBlackList);
        return accountService.save(account);
    }
    @DeleteMapping("/{id}")
    public void deleteOne(@PathVariable long id){
        accountService.deleteById(id);
    }

    //和前端配合，让用户页面进行跳转到登录用户界面


    @Autowired private EmailService emailService;
    @PostMapping("/forgetPassword")
    public String sendVerificationMail(String userMail){
        if(accountService.findAccountByUserMail(userMail)==null)return "不存在该账户";
        return emailService.sendEmail(userMail);
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
