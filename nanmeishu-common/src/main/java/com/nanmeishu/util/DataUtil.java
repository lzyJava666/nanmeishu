package com.nanmeishu.util;

import java.security.MessageDigest;

/**
 * 数据验证工具类
 */
public class DataUtil {
    /**
     * 数据有效性验证---Object类型
     * @param obj 需有效性验证的数据
     */
    public static void verifyData(Object obj,String mes){
        if(obj==null){
            throw new RuntimeException("传入的数据：("+mes+") 为空值，请重新操作！");
        }else{
            if(obj instanceof String){
                if(obj.equals("")){
                    throw new RuntimeException("传入的数据：("+mes+") 为空值，请重新操作！");
                }
            }
        }
    }

    /**
     * 数据有效性验证---传入的数据不得与指定数据相等
     * @param obj 有效性验证的数据
     * @param noObj 指定数据
     */
    public static void verifyDataString(String obj,String noObj,String mes){
        verifyData(obj,mes);
        if(!obj.equals(noObj)){
            throw new RuntimeException("传入的数据：("+mes+" )不满足条件，请检查数据的合法性！");
        }
    }

    /**
     * 用于对字符串加密的方法
     * @param ss
     * @return
     */
    public static String md5Encrypt(String ss,String KEY) {
        ss=ss==null?"":ss+KEY;
        char[] md5Digist= {'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};//字典
        try {
            MessageDigest md= MessageDigest.getInstance("MD5");
            byte[] ssarr=ss.getBytes();
            md.update(ssarr);
            byte[] mssarr=md.digest();

            int len=mssarr.length;
            char[] str=new char[len*2];
            int k=0;
            for(int i=0;i<len;i++) {
                byte b=mssarr[i];
                str[k++]=md5Digist[b >>> 4 & 0xf];
                str[k++]=md5Digist[b & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
