package com.pbrs.servlet;
/*
Date: 5/28/2024
Author: lu0qlng
Description: 获取某一本书
*/

import com.alibaba.fastjson.JSONObject;
import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.utils.JsonUtils;
import com.pbrs.utils.MapperUtils;
import org.apache.lucene.search.similarities.Lambda;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;

@WebServlet("/getOneBook")
public class GetOneBook extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        JsonUtils.unifyCharaEncode(req, res);

        int bookId = Integer.parseInt(req.getParameter("bookId"));

        DataMapper dataMapper = MapperUtils.getMapper(DataMapper.class);
        Data data = dataMapper.selectOneByIndex(bookId);
        data.fillNone();

        res.getWriter().write(JSONObject.toJSONString(data));
    }
}
