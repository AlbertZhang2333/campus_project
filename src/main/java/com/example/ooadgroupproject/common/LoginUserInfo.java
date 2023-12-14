package com.example.ooadgroupproject.common;

import com.example.ooadgroupproject.entity.Account;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class LoginUserInfo {
   private static Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
   public static Account getAccount(){
       return (Account) authentication.getPrincipal();
   }
}
