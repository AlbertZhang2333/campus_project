package com.example.campus.controller;

import com.example.campus.entity.Account;
import com.example.campus.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/exer")
public class AccountController {
    @Autowired
    //jpa会自动依照规范的命名确定出所需求的简单功能，并直接将之实现和实例化
    private AccountService accountService;//=new AccountServiceImpl();new部分可写可不写
//    @GetMapping("/record")
//    public List<Account>findAll(){
//        System.out.println(accountService.getClass().getName());
//        return accountService.findAll();
//    }
    //该条后期应当归入到其他对应的模块当中
    @PostMapping("/record")
    public Account addOne(Account account){
        return accountService.save(account);
    }
    @PutMapping("/record")
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
    @DeleteMapping("record/{id}")
    public void deleteOne(@PathVariable long id){
        accountService.deleteById(id);
    }

    //和前端配合，让用户页面进行跳转到登录用户界面
    @GetMapping
    public String loginPage(){
        return "recode/loginCheck";
    }



    //向网页请求信息，并完成用户的登录
    @PostMapping("/record/loginCheck")
    public String AccountLogin(@RequestParam String userMail,
                                @RequestParam String username,
                                @RequestParam String password){
        Account account=accountService.AccountLogin(userMail,username,password);
        if(account==null){
            System.out.println("400 尝试登录的用户信息不存在");
            return "user";
        }else {
            System.out.println(account);
            return "loginFailed";
        }
    }





//10.11基础的登录所需的查询用户是否存在已经实现。现在需要考虑如何对网络页面做出相应的响应。
    //考虑从HttpSession上入手



}
