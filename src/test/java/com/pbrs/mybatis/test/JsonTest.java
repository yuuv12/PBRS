package com.pbrs.mybatis.test;
/*
Date: 5/25/2024
Author: lu0qlng
Description: 测试Fast-Json包
*/

import com.alibaba.fastjson.JSONObject;
import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.utils.MapperUtils;
import org.junit.Test;

import java.util.List;

public class JsonTest {
    @Test
    public void testJson(){
        DataMapper mapper = MapperUtils.getMapper(DataMapper.class);
        List<Data> lst = mapper.selects("蓝色", "家");
        System.out.println(JSONObject.toJSONString(lst));
    }
}
