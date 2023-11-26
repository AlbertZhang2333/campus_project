package com.example.ooadgroupproject;

//该文件用于声明出用户的权限等级，如果后续有需求，可以在这里调整用户的等级类别
public class IdentityLevel {
    public String LevelCheck(int identity){
        if(identity==VISITOR){
            return "VISITOR";
        }else if(identity==NORMAL_USER){
            return "NORMAL_USER";
        }else if(identity==ACCOUNT_ADMIN){
            return "ACCOUNT_ADMIN";
        }else {
            return "ERROR";
        }
    }
    public static final int VISITOR=0;
    public static final int NORMAL_USER=1;
    public static final int ACCOUNT_ADMIN=2;

}