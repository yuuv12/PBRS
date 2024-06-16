package com.pbrs.utils;
/*
Date: 5/28/2024
Author: lu0qlng
Description: 封装常用的json操作
*/

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class JsonUtils {
    JSONObject search_limit = new JSONObject();
    public static void unifyCharaEncode(ServletRequest servletRequest, ServletResponse servletResponse) throws UnsupportedEncodingException {
        servletRequest.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json;charset=UTF-8");
    }

    public JsonUtils(ServletRequest servletRequest, ServletResponse servletResponse) throws IOException {
        unifyCharaEncode(servletRequest, servletResponse);

        BufferedReader reader = servletRequest.getReader();
        StringBuilder paramsStr = new StringBuilder();

        String nextLine;
        while ((nextLine = reader.readLine()) != null) {
            paramsStr.append(nextLine);
        }
        if (!paramsStr.isEmpty()) {
            search_limit = JSON.parseObject(String.valueOf(paramsStr));
        }
    }

    public boolean isEmpty(){
        return !search_limit.isEmpty();
    }

    public String getString(String paramName){
        return search_limit.getString(paramName);
    }

}
