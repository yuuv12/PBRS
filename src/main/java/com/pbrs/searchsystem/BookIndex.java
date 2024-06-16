package com.pbrs.searchsystem;

/*
Date: 5/24/2024
Author: lu0qlng
Description: 索引库维护、创建索引，使用
*/


import com.pbrs.mybatis.mapper.DataMapper;
import com.pbrs.mybatis.pojo.Data;
import com.pbrs.utils.MapperUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * 索引库维护
 */
public class BookIndex {
    /*
     * 创建索引库
     */
    public void createIndexTest() throws Exception {
        //1. 采集数据

        DataMapper mp = MapperUtils.getMapper(DataMapper.class);
        List<Data> bookList = mp.queryAllData();

        //文档集合
        List<Document> docList = new ArrayList<>();

        for (Data book : bookList) {
            //2. 创建文档对象
            
            Document document = new Document();
            book.fillNone();

            //创建域对象并且放入文档对象中
            /*
              是否分词: 否, 因为主键分词后无意义
              是否索引: 是, 如果根据serial_number主键查询, 就必须索引
              是否存储: 是, 因为主键serial_number比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
                   存储后, 才可以获取到serial_number具体的内容
             */
            document.add(new StringField("serial_number", String.valueOf(book.getSerial_number()), Field.Store.YES));

            /*
              是否分词: 是, 用于进行查询
              是否索引: 是, 如果根据book_name查询, 就必须索引
              是否存储: 是, 因为book_name比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
                   存储后, 才可以获取到book_name具体的内容
             */
            document.add(new TextField("book_name", book.getBook_name(), Field.Store.YES));

            /*
             * 是否分词: 是,用于进行查询
             * 是否索引: 是, 如果根据author查询, 就必须索引
             * 是否存储: 是, 因为author比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到author具体的内容
             */
            document.add(new TextField("author", book.getAuthor(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为颜色分词没有意义
             * 是否索引: 是, 如果根据cover_color查询, 就必须索引
             * 是否存储: 是, 因为cover_color比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到cover_color具体的内容
             */
            document.add(new StringField("cover_color", book.getCover_color(), Field.Store.YES));

            /*
             * 是否分词: 是
             * 是否索引: 是, 如果根据awards查询, 就必须索引
             * 是否存储: 是, 因为awards比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到awards具体的内容
             */
            document.add(new TextField("awards", book.getAwards(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为主键分词后无意义
             * 是否索引: 是, 如果根据page_number查询, 就必须索引
             * 是否存储: 是, 因为page_number比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到page_number具体的内容
             */
            document.add(new StringField("page_number", String.valueOf(book.getPage_number()), Field.Store.YES));

            /*
             * 是否分词: 否, 适读年龄分词并无意义
             * 是否索引: 是, 如果根据age_appropriate查询, 就必须索引
             * 是否存储: 是, 因为age_appropriate比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到age_appropriate具体的内容
             */
            document.add(new StringField("age_appropriate", book.getAge_appropriate(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为这个不用于查询
             * 是否索引: 是, 如果根据is_series查询, 就必须索引
             * 是否存储: 是, 因为is_series比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到is_series具体的内容
             */
            document.add(new StringField("is_series", String.valueOf(book.getIs_series()), Field.Store.YES));

            /*
             * 是否分词: 是, 查询需要
             * 是否索引: 是, 如果根据introduction查询, 就必须索引
             * 是否存储: 是, 因为introduction比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到introduction具体的内容
             */
            document.add(new TextField("introduction", book.getIntroduction(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为主键分词后无意义
             * 是否索引: 是, 如果根据book_type查询, 就必须索引
             * 是否存储: 是, 因为book_type比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到book_type具体的内容
             */
            document.add(new StringField("book_type", book.getBook_name(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为主键分词后无意义
             * 是否索引: 是, 如果根据have_cover_pic查询, 就必须索引
             * 是否存储: 是, 因为have_cover_pic比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到have_cover_pic具体的内容
             */
            document.add(new StringField("have_cover_pic", String.valueOf(book.getHave_cover_pic()), Field.Store.YES));

            /*
             * 是否分词: 是, 查询需要
             * 是否索引: 是, 如果根据main_character_animals查询, 就必须索引
             * 是否存储: 是, 因为main_character_animals比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到main_character_animals具体的内容
             */
            document.add(new TextField("main_character_animals", book.getMain_character_animals(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据main_character_plants查询, 就必须索引
             * 是否存储: 是, 因为main_character_plants比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到main_character_plants具体的内容
             */

            document.add(new TextField("main_character_plants", book.getMain_character_plants(), Field.Store.YES));

            /*
             * 是否分词: 是, 查询需要
             * 是否索引: 是, 如果根据main_character_figures查询, 就必须索引
             * 是否存储: 是, 因为main_character_figures比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到main_character_figures具体的内容
             */
            document.add(new TextField("main_character_figures", book.getMain_character_figures(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据main_character_foods查询, 就必须索引
             * 是否存储: 是, 因为main_character_foods比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到main_character_foods具体的内容
             */
            document.add(new TextField("main_character_foods", book.getMain_character_foods(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据main_character_vehicles查询, 就必须索引
             * 是否存储: 是, 因为main_character_vehicles比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到main_character_vehicles具体的内容
             */
            document.add(new TextField("main_character_vehicles", book.getMain_character_vehicles(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据main_character_others查询, 就必须索引
             * 是否存储: 是, 因为main_character_others比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到main_character_others具体的内容
             */
            document.add(new TextField("main_character_others", book.getMain_character_others(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据protagonist_features查询, 就必须索引
             * 是否存储: 是, 因为protagonist_features比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到protagonist_features具体的内容
             */
            document.add(new TextField("protagonist_features", book.getProtagonist_features(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为主键分词后无意义
             * 是否索引: 是, 如果根据protagonist_relations查询, 就必须索引
             * 是否存储: 是,

             因为protagonist_relations比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到protagonist_relations具体的内容
             */
            document.add(new TextField("protagonist_relations", book.getProtagonist_relations(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据time_factors查询, 就必须索引
             * 是否存储: 是, 因为time_factors比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到time_factors具体的内容
             */
            document.add(new TextField("time_factors", book.getTime_factors(), Field.Store.YES));

            /*
             * 是否分词:  是, 查询需要
             * 是否索引: 是, 如果根据location_factors查询, 就必须索引
             * 是否存储: 是, 因为location_factors比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到location_factors具体的内容
             */
            document.add(new TextField("location_factors", book.getLocation_factors(), Field.Store.YES));

            /*
             * 是否分词: 否, 因为主键分词后无意义
             * 是否索引: 是, 如果根据have_iconic_items查询, 就必须索引
             * 是否存储: 是, 因为have_iconic_items比较特殊, 可以确定唯一的一条数据, 在业务上一般有重要所用, 所以存储
             *      存储后, 才可以获取到have_iconic_items具体的内容
             */
            document.add(new StringField("have_iconic_items", String.valueOf(book.getHave_iconic_items()), Field.Store.YES));


            //将文档对象放入到文档集合中
            docList.add(document);
        }
        //3. 创建分词器,使用IKA中文分词器, 中文分词效果较好
        Analyzer analyzer = new IKAnalyzer();

        //4. 创建Directory目录对象, 目录对象表示索引库的位置
        Directory  dir = FSDirectory.open(Paths.get("src/main/resources/index-resources"));
        //5. 创建IndexWriterConfig对象, 这个对象中指定切分词使用的分词器
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        //6. 创建IndexWriter输出流对象, 指定输出的位置和使用的config初始化对象
        IndexWriter indexWriter = new IndexWriter(dir, config);
        //7. 写入文档到索引库
        for (Document doc : docList) {
            indexWriter.addDocument(doc);
        }
        //8. 释放资源
        indexWriter.close();
    }


}
