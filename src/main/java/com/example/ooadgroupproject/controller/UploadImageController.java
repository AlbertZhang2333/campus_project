package com.example.ooadgroupproject.controller;

import com.example.ooadgroupproject.Utils.ManageAccountUtil;
import com.example.ooadgroupproject.common.Result;
import com.example.ooadgroupproject.entity.Account;
import com.example.ooadgroupproject.service.Impl.UploadFileServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

@RestController
@RequestMapping("/uploadImage")
public class UploadImageController {

    @Autowired
    private UploadFileServiceImpl uploadFileService;

    @PostMapping("/image")
    public Result batchAddAccount(@RequestParam("file") MultipartFile file, @RequestParam("path") String path) throws IOException {
        if (file == null || file.isEmpty()) {
            return Result.fail("所选文件为空或还未选择文件");
        }
        System.out.println(file);
        System.out.println(path);
        path = "E:/ooad/campus_front/" + path;

        String uploadResult = uploadFileService.uploadFile(file, path);

        String[] s = uploadResult.split("\\\\");

//        System.out.println(666);

//        System.out.println(777);

//        System.out.println(Arrays.toString(s));
//        System.out.println(uploadResult);

        return Result.success(s[s.length - 1]);
    }
}
