package com.nanmeishu.upload.controller;

import com.google.gson.Gson;
import com.nanmeishu.upload.utils.QiNiuUtils;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/upload")
public class UploadController {

    public static void main(String[] args) {
        System.out.println(QiNiuUtils.uploadByPath("C:\\Users\\admin\\Desktop\\材料整理\\心情素材\\烦躁.png"));
    }

    @ApiOperation("文件上传接口")
    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(file);
        InputStream inputStream = file.getInputStream();
        String s = QiNiuUtils.uploadByInputStream(inputStream);
        return s;
    }
}
