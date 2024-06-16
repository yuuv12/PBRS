package com.pbrs.mybatis.test;
/*
Date: 5/24/2024
Author: lu0qlng
Description: 测试数据库
*/

import com.alibaba.fastjson.JSONObject;
import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.mapper.SimilarityMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.mybatis.pojo.Similarity;
import com.pbrs.utils.MapperUtils;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MybatisTest {
    @Test
    public void testMybatis() throws IOException {
        DataMapper mapper = MapperUtils.getMapper(DataMapper.class);
        List<Data> lst = mapper.selects("灰色", "%");

        lst.forEach(System.out::println);

    }
    @Test
    public void testSimilarity() throws IOException{
        SimilarityMapper mapper = MapperUtils.getMapper(SimilarityMapper.class);
        Similarity similarity = mapper.selectSimilarity(68);
        System.out.println(similarity);
        if (similarity != null){
            System.out.println(similarity.getList());
        }

        List<Integer> idxList = new ArrayList<>();

        if (similarity != null){
            idxList = similarity.getList();
            String sq = JSONObject.toJSONString(idxList).replace("[", "(").replace("]", ")");
            System.out.println(sq);
            DataMapper mp = MapperUtils.getMapper(DataMapper.class);
            List<Data> lst = mp.selectSimilarBook(sq);


            lst.forEach(System.out::println);
        }

        System.out.println(JSONObject.toJSONString(idxList));

    }
}
