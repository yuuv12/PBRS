package com.pbrs.mybatis.mapper;
/*
Date: 6/14/2024
Author: lu0qlng
Description: 
*/

import com.pbrs.mybatis.pojo.Similarity;
import org.apache.ibatis.annotations.Param;

public interface SimilarityMapper {
    Similarity selectSimilarity(@Param("index") int index);

}
