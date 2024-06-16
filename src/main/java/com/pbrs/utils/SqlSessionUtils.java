package com.pbrs.utils;
/*
Date: 5/24/2024
Author: lu0qlng
Description: 封装获取SqlSession类实体
*/

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

public class SqlSessionUtils {
    public static SqlSession getSqlSession(){
        SqlSession sqlSession = null;
        try {
            InputStream is = Resources.getResourceAsStream("Mybatis-config.xml");
            SqlSessionFactoryBuilder builder = new SqlSessionFactoryBuilder();
            SqlSessionFactory factory = builder.build(is);
            sqlSession = factory.openSession();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return sqlSession;
    }
}
