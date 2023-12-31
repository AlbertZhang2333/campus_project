package com.example.ooadgroupproject.common;

import com.example.ooadgroupproject.IdentityLevel;
import com.example.ooadgroupproject.entity.Account;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserInfo {
   private static Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
   public static Account getAccount(){
       Object auth=authentication;
       //判断是否为匿名登录,此处在实际启动权限管理后将删除，仅为方便测试而存在
       if(auth instanceof AnonymousAuthenticationToken){
           Account anonymousAccount=new Account();
           anonymousAccount.setUsername("anonymous");
           anonymousAccount.setUserMail("null");
           anonymousAccount.setIdentity(IdentityLevel.VISITOR);
           anonymousAccount.setUserIcon(0);
           return anonymousAccount;
       }

       return (Account) authentication.getPrincipal();
   }
}
