package com.nanmeishu.util;

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
}
