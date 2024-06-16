package com.pbrs.servlet;
/*
Date: 6/14/2024
Author: lu0qlng
Description: 获取相似的书籍
*/

import com.alibaba.fastjson.JSONObject;
import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.mapper.SimilarityMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.mybatis.pojo.Similarity;
import com.pbrs.utils.JsonUtils;
import com.pbrs.utils.MapperUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/getSimilarBook")
public class GetSimilarBook extends HttpServlet {
    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        JsonUtils.unifyCharaEncode(req, res);

        String sql = req.getParameter("bookId");
        int bookId = Integer.parseInt(sql);

        SimilarityMapper mapper = MapperUtils.getMapper(SimilarityMapper.class);
        Similarity similarity = mapper.selectSimilarity(bookId);

        List<Data> lst = new ArrayList<>();

        if (similarity != null){
            List<Integer> idxList = new ArrayList<>();
            idxList = similarity.getList();
            String sq = JSONObject.toJSONString(idxList).replace("[", "(").replace("]", ")");

            DataMapper mp = MapperUtils.getMapper(DataMapper.class);
            lst = mp.selectSimilarBook(sq);
        }

        res.getWriter().write(JSONObject.toJSONString(lst));
    }
}
