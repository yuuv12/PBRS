package com.pbrs.servlet;
/*
Date: 5/29/2024
Author: lu0qlng
Description: 通过前端分面形成的sql语句进行查找
*/

import com.alibaba.fastjson.JSONObject;
import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.utils.JsonUtils;
import com.pbrs.utils.MapperUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchAny")
public class SqlGetBook extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        JsonUtils.unifyCharaEncode(req, res);

        String sql = req.getParameter("sql");

        DataMapper dataMapper = MapperUtils.getMapper(DataMapper.class);
        List<Data> dataList = dataMapper.selectBySql(sql);
        dataList.forEach(Data::fillNone);

        res.getWriter().write(JSONObject.toJSONString(dataList));
    }
}
