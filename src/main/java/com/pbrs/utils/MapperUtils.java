package com.pbrs.utils;
/*
Date: 5/24/2024
Author: lu0qlng
Description: 封装获取Mapper类实体
*/

import org.apache.ibatis.session.SqlSession;

import static com.pbrs.utils.SqlSessionUtils.getSqlSession;

public class MapperUtils {
    public static <T> T getMapper(Class<T> implement){
        SqlSession sqlSession = getSqlSession();
        return sqlSession.getMapper(implement);
    }
}
