package com.example.ooadgroupproject.service.Impl;

import com.example.ooadgroupproject.common.LoginUserInfo;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.UploadFileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.sql.Date;
import java.time.LocalDateTime;

@Service
public class UploadFileServiceImpl implements UploadFileService {


    public String uploadFile(MultipartFile file,String path) throws IOException {
        InputStream inputStream=null;
        BufferedInputStream reader=null;
        FileOutputStream outputStream=null;
        BufferedOutputStream writer=null;
        try {
            inputStream = file.getInputStream();
            reader=new BufferedInputStream(inputStream);
            long time = System.currentTimeMillis();
            String account_Username= LoginUserInfo.getAccount().getUsername();

            File localFile=new File(path+"/"+time+"_" + account_Username + "_" + file.getOriginalFilename());
            localFile.createNewFile();
            outputStream = new FileOutputStream(localFile);
            writer=new BufferedOutputStream(outputStream);
            byte[] buffer = new byte[2048];
            int bytesRead;
            while ((bytesRead = reader.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            return localFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }finally {
            if(inputStream!=null){
                inputStream.close();
            }
            if(reader!=null){
                reader.close();
            }
            if(outputStream!=null){
                outputStream.close();
            }
            if(writer!=null){
                writer.close();
            }
        }
    }

}