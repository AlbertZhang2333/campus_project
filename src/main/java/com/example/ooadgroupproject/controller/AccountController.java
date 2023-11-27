package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.AccountService;
import com.example.ooadgroupproject.service.EmailService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.example.ooadgroupproject.IdentityLevel;

import java.util.List;

@RestController
@RequestMapping("/login")

public class AccountController {
    @Autowired
    //jpa会自动依照规范的命名确定出所需求的简单功能，并直接将之实现和实例化
    private AccountService accountService;//=new AccountServiceImpl();new部分可写可不写

    //该条后期应当归入到其他对应的模块当中
//    @PostMapping("/register")
//    public Account addOne(Account account){
//        return accountService.save(account);
//    }
    @GetMapping("/test")
    public List<Account> findAll(){
        return accountService.findAll();
    }
    @PostMapping("/test")
    public void deleteAll(){
        for(int i=0;i<100;i++) {
            accountService.deleteById(i);
        }
    }
    @PutMapping("/register")
    public Account addNewAccount(@RequestParam String username,
                                 @RequestParam String userMail,
                                 @RequestParam String password){
        Account account=new Account();
        account.setIdentity(IdentityLevel.VISITOR);
        account.setPassword(password);
        account.setUserMail(userMail);
        account.setUsername(username);
        account.setInBlackList(false);
        return accountService.save(account);

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


    //向网页请求信息，并完成用户的登录
    //11.26添加cookie与seesion
    @PostMapping("/loginCheck")
    public Account AccountLogin(@RequestParam String userMail,
                                @RequestParam String username,
                                @RequestParam String password, HttpSession httpSession, HttpServletResponse response){
        Account account=accountService.AccountLogin(userMail,username,password);
        if(account==null){
            System.out.println("400 尝试登录的用户信息不存在");
            throw new ServiceException("账号不存在");
        }else {
            System.out.println(account);
            loginCookieAndSessionSet(httpSession,response,account,password);
            return account;
        }
    }



    @GetMapping("/auto-login")
    public String autoLogin(@CookieValue(value ="userMail",required = false)String userMail,
                            @CookieValue(value = "username",required = false)String username,
                            @CookieValue(value = "password",required = false)String password,
                            HttpSession httpSession,HttpServletResponse response){
        Account account=accountService.AccountLogin(userMail,username,password);
        if(account!=null){
            httpSession.setAttribute("userId",account.getId());
            loginCookieAndSessionSet(httpSession,response,account,password);
            return "Auto login success!";
        }
        return userMail+" "+username+" "+password;
    }
    public void loginCookieAndSessionSet(HttpSession httpSession,HttpServletResponse response,
                                    Account account,String password){
        Cookie cookieMail=new Cookie("userMail",account.getUserMail());
        Cookie cookieName=new Cookie("username",account.getUsername());
        Cookie cookiePassword=new Cookie("password",password);
        Cookie[] cookies=new Cookie[]{cookieMail,cookieName,cookiePassword};
        for (Cookie cookie : cookies) {
            cookie.setMaxAge(24*7*60 * 60);
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setPath("/");//用户信息的cookie应该全局可见
            response.addCookie(cookie);
        }
        httpSession.setAttribute("userId",account.getId());
    }

    @Autowired private EmailService emailService;
    @PostMapping("/forgetPassword")
    public String sendVerificationMail(String userMail){
        if(accountService.findAccountByUserMail(userMail)==null)return "不存在该账户";
        return emailService.sendEmail(userMail);
    }
    @PostMapping("/verificationEmail")
    public String verifyMailAndChangePassword(String code,String userMail,String password){
        if(emailService.verifyCode(code)) {
            Account account= accountService.findAccountByUserMail(userMail);
            account.setPassword(password);
            accountService.save(account);
            return password;
        }else {
            throw new Error("邮箱验证码输入错误");
        }
    }


}
