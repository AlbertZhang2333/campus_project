package com.example.ooadgroupproject;
import com.example.ooadgroupproject.common.MyGrantedAuthority;
import org.springframework.security.core.GrantedAuthority;


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//该文件用于声明出用户的权限等级，如果后续有需求，可以在这里调整用户的等级类别
public class IdentityLevel {
    public String LevelCheck(int identity){
        if(identity==VISITOR){
            return roleVisitor;
        }else if(identity==NORMAL_USER){
            return roleNormalUser;
        }else if(identity==ACCOUNT_ADMIN){
            return roleAccountAdmin;
        }else {
            return "ERROR";
        }
    }
    public static final int VISITOR=0;
    public static final int NORMAL_USER=1;
    public static final int ACCOUNT_ADMIN =2;
    public static final String roleVisitor="ROLE_ANONYMOUS";
    public static final String roleNormalUser="ROLE_NORMAL_USER";
    public static final String roleAccountAdmin="ROLE_ACCOUNT_ADMIN";


    public static Collection<? extends GrantedAuthority> getAuthorities(int identity) {
        ArrayList<MyGrantedAuthority>authorities=new ArrayList<>();
        for(int i=identity;i>=0;i--){
            authorities.add(new MyGrantedAuthority(i));
        }
        return authorities;
    }

//    public static List<String> allowedUrl(String role){
//        if(role.equals(roleVisitor)){
//            return new ArrayList<>(){""};
//        }else if(role.equals(roleNormalUser)){
//
//        }else {
//
//        }
//
//    }




}