package com.example.ooadgroupproject.service;

import com.example.ooadgroupproject.common.Result;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface UploadFileService {
    public String uploadFile(MultipartFile file) throws IOException;
}
