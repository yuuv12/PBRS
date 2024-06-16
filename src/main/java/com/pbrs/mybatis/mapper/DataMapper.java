package com.pbrs.mybatis.mapper;
/*
Date: 5/24/2024
Author: lu0qlng
Description: 抽象类，用于写各种数据库操作的方法，需要与resources下的DataMapper.xml一一对应，并写明映射类
*/

import com.pbrs.mybatis.pojo.Data;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DataMapper {
    List<Data> selects(@Param("cover_color") String cover_color, @Param("location_factors") String location_factors);
    List<Data> queryAllData();
    Data selectOneByIndex(@Param("index") int index);
    List<Data> selectBySql(@Param("sql") String sql);
    Data selectOneByIndexAndArgs(@Param("index") int index, @Param("args") String args);
    List<Data> selectSimilarBook(@Param("index") String index);
}
