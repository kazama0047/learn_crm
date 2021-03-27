package com.crm.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kazama
 * @create 2021-03-27-23:26
 */
public class Result {
    public static Map<String,Boolean> returnFlag(boolean flag){
        Map<String, Boolean> result = new HashMap<>();
        result.put("success",flag);
        return result;
    }
    public static Map<String,Object> returnObject(){
        return new HashMap<String,Object>();
    }
}
