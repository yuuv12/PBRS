package com.pbrs.servlet;
/*
Date: 5/27/2024
Author: lu0qlng
Description:
*/


import com.alibaba.fastjson.JSONObject;
import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.utils.JsonUtils;
import com.pbrs.utils.MapperUtils;

import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

@WebServlet("/searchString")
public class ParentSearch extends HttpServlet {


    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        final float RELEVANCE_THRESHOLD = 0.1f;
        JsonUtils jsonUtils = new JsonUtils(servletRequest, servletResponse);
        //1. 创建分词器(对搜索的关键词进行分词使用)
        //注意: 分词器要和创建索引的时候使用的分词器一模一样
        String ask = "妙想科学";
        if (jsonUtils.isEmpty()){
            ask = jsonUtils.getString("ask");
//            System.out.println(ask);
        }

        IKAnalyzer analyzer = new IKAnalyzer();

        //需求: 使用各个字段对于用户搜索的关键词进行查询
        //查询的多个域名
        Query query = getQuery(analyzer, ask);

        //4. 创建Directory目录对象, 指定索引库的位置
        Directory dir = FSDirectory.open(Paths.get("../../../resources/index-resources"));
        //5. 创建输入流对象
        IndexReader indexReader = DirectoryReader.open(dir);
        //6. 创建搜索对象
        IndexSearcher indexSearcher = new IndexSearcher(indexReader);
        //7. 搜索, 并返回结果
        //第二个参数: 是返回多少条数据用于展示, 分页使用
        TopDocs topDocs = indexSearcher.search(query, 150);

//        //获取查询到的结果集的总数, 打印
//        System.out.println("=======count=======" + topDocs.totalHits);

        //8. 获取结果集
//        ScoreDoc[] scoreDocs = topDocs.scoreDocs;
        ScoreDoc[] beforeFilterScoreDocs = topDocs.scoreDocs;
        List<ScoreDoc> scoreDocs = new ArrayList<>();
        for (ScoreDoc scoreDoc : beforeFilterScoreDocs) {
            if (scoreDoc.score >= RELEVANCE_THRESHOLD) {
                System.out.println(scoreDoc.score);
                scoreDocs.add(scoreDoc);
            }
        }

        int[] docIDs = new int[(int) topDocs.totalHits];

        if (scoreDocs != null) {
            int i = 0;
            for (ScoreDoc scoreDoc : scoreDocs) {
                //获取查询到的文档唯一标识, 文档id, 这个id是lucene在创建文档的时候自动分配的
                int docID = scoreDoc.doc;
                if (docID < 1105){
                    boolean flag = false;
                    for (int id : docIDs){
                        if (id == docID){
                            flag = true;
                            break;
                        }
                    }
                    if (!flag){
                        docIDs[i] = docID;
                        i += 1;
                    }
                }
            }
        }


        int i = docIDs.length;
        for (;i>0; i--){
            if (docIDs[i-1]!=0){
                break;
            }
        }

        docIDs = Arrays.copyOf(docIDs, i);
        //10. 关闭流
        analyzer.close();

        DataMapper mapperUtils = MapperUtils.getMapper(DataMapper.class);
        List<Data> dataList = new ArrayList<>();

        String args = null;
        if (jsonUtils.isEmpty()){
            args = jsonUtils.getString("args");
        }

        for (int docID : docIDs){
            Data data = null;
            if(Objects.equals(args, "")){
                data = mapperUtils.selectOneByIndex(docID);
            } else {
                data = mapperUtils.selectOneByIndexAndArgs(docID, args);
            }
            if (data != null){
                data.fillNone();
                dataList.add(data);
            }
        }

        servletResponse.getWriter().write(JSONObject.toJSONString(dataList));
    }

    private static Query getQuery(IKAnalyzer analyzer, String ask) {
        String[] fields = {
                "book_name",
                "author",
                "cover_color",
                "awards",
                "introduction",
                "book_type",
                "main_character_animals",
                "main_character_plants",
                "main_character_figures",
                "main_character_foods",
                "main_character_vehicles",
                "main_character_others",
                "protagonist_features",
                "protagonist_relations",
                "time_factors",
                "location_factors",
        };
        //设置影响排序的权重, 这里设置域的权重
        MultiFieldQueryParser multiFieldQueryParser = getMultiFieldQueryParser(analyzer, fields);
        //设置查询的关键词
        Query query = null;
        try {
            query = multiFieldQueryParser.parse(ask);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return query;
    }

    private static MultiFieldQueryParser getMultiFieldQueryParser(IKAnalyzer analyzer, String[] fields) {
        Map<String, Float> boots = new HashMap<>();
        boots.put("book_name", 1.8f); // 书名的重要性较高
        boots.put("author", 2f); // 作者的重要性较高
        boots.put("cover_color", 0.6f); // 封面颜色的重要性中等
        boots.put("awards", 1.8f); // 奖项的重要性较高
        boots.put("introduction", 0.4f); // 简介的重要性中等
        boots.put("book_type", 0.8f); // 书籍类型的重要性较高
        boots.put("main_character_animals", 0.5f); // 主要角色是动物的重要性中等
        boots.put("main_character_plants", 0.5f); // 主要角色是植物的重要性中等
        boots.put("main_character_figures", 0.5f); // 主要角色是人物的重要性中等
        boots.put("main_character_foods", 0.5f); // 主要角色是食物的重要性中等
        boots.put("main_character_vehicles", 0.5f); // 主要角色是交通工具的重要性中等
        boots.put("main_character_others", 0.5f); // 主要角色是其他类型的重要性中等
        boots.put("protagonist_features", 0.4f); // 主人公特征的重要性中等偏下
        boots.put("protagonist_relations", 0.4f); // 主人公关系的重要性中等偏下
        boots.put("time_factors", 0.45f); // 时间因素的重要性中等偏下
        boots.put("location_factors", 0.45f); // 地点因素的重要性中等偏下
        //从多个域查询对象
        return new MultiFieldQueryParser(fields, analyzer, boots);
    }
}
