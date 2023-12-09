package com.example.ooadgroupproject.Utils;

import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class ManageAccountUtil {
    public static ArrayList<Account>accountList;
    //调用该方法后，返回的新增account，将存储在accountList中
    //成功存储将返回null,否则返回Result.fail
    public static Result batchAddAccount(String localFilePath) throws IOException {
        ManageAccountUtil.accountList=new ArrayList<>();
        if(localFilePath==null){
            return Result.fail("文件上传失败");
        }
        File localFile=new File(localFilePath);
        BufferedReader reader=new BufferedReader(new FileReader(localFile));
        String line;
        line=reader.readLine();
        String[]header=line.split(",");
        HashMap<String,Integer> infoPos=new HashMap<>();
        if(header.length!=4){
            return Result.fail("文件格式不符，请检查文件格式");
        }else {
            for(int i=0;i<4;i++){
                String temp=header[i];
                switch (temp) {
                    case "username", "userMail", "password","identity" -> infoPos.put(temp, i);
                    default -> {
                        return Result.fail("文件格式不符，请检查文件格式");
                    }
                }
            }
        }
        if(infoPos.size()!=4){
            return Result.fail("文件格式不符，请检查文件格式");
        }
        long count=1L;
        //接下来，创建大量的Account
        while((line= reader.readLine())!=null){
            count++;
            String[]info=line.split(",");
            String username=info[infoPos.get("username")];
            String userMail=info[infoPos.get("userMail")];
            String password=info[infoPos.get("password")];
            int Identity= Integer.parseInt(info[infoPos.get("identity")]);
            Account account=new Account(username,userMail,password,Identity);
            accountList.add(account);
        }
        return null;
    }
}
