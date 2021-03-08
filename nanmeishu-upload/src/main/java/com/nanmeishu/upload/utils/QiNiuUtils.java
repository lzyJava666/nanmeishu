package com.nanmeishu.upload.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.SQLOutput;

public class QiNiuUtils {

    public static Configuration cfg = new Configuration(Region.region0());

    public static UploadManager uploadManager = new UploadManager(cfg);

    /**
     * 通过指定路径上传文件
     * @param path 路径
     * @return 返回url
     */
    public static String uploadByPath(String path){
        String urlPath="";
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        Auth auth = Auth.create(UploadKey.accessKey, UploadKey.secretKey);
        String upToken = auth.uploadToken(UploadKey.bucket);
        try {
            Response response = uploadManager.put(path, key, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            urlPath=putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return urlPath;
    }

    /**
     * 通过InputStream上传文件
     * @param inputStream 流
     * @return 返回url
     */
    public static String uploadByInputStream(InputStream inputStream){
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        String path="";
        try {
            Auth auth = Auth.create(UploadKey.accessKey, UploadKey.secretKey);
            String upToken = auth.uploadToken(UploadKey.bucket);
            try {
                Response response = uploadManager.put(inputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                path=putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return path;
    }
}
