package test;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.cjk.CJKAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Paths;

/**
 * 测试分词器
 */
public class TestAnalyzer {

    /**
     * 去掉空格分词器, 不支持中文
     * @throws Exception
     */
    @Test
    public void TestWhitespaceAnalyzer() throws Exception{
        // 1. 创建分词器,分析文档，对文档进行分词
        Analyzer analyzer = new WhitespaceAnalyzer();

        // 2. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("E:\\dir"));

        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // 4.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 5.写入到索引库，通过IndexWriter添加文档对象document
        Document doc = new Document();
        doc.add(new TextField("name", "vivo X23 8GB+128GB 幻夜蓝", Field.Store.YES));
        indexWriter.addDocument(doc);

        // 6.释放资源
        indexWriter.close();
    }

    /**
     * 简单分词器: 不支持中文, 将除了字母之外的所有符号全部取出, 所有大写字母转换成小写字母, 对于数字也会去除
     * @throws Exception
     */
    @Test
    public void TestSimpleAnalyzer() throws Exception{
        // 1. 创建分词器,分析文档，对文档进行分词
        Analyzer analyzer = new SimpleAnalyzer();

        // 2. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("E:\\dir"));

        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // 4.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 5.写入到索引库，通过IndexWriter添加文档对象document
        Document doc = new Document();
        doc.add(new TextField("name", "vivo，X23。 8GB+128GB； 幻夜蓝", Field.Store.YES));
        indexWriter.addDocument(doc);

        // 6.释放资源
        indexWriter.close();
    }

    /**
     * 中日韩分词器: 使用二分法分词, 去掉空格, 去掉标点符号, 所有大写字母转换成小写字母
     * @throws Exception
     */
    @Test
    public void TestCJKAnalyzer() throws Exception{
        // 1. 创建分词器,分析文档，对文档进行分词
        Analyzer analyzer = new CJKAnalyzer();

        // 2. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("E:\\dir"));

        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // 4.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 5.写入到索引库，通过IndexWriter添加文档对象document
        Document doc = new Document();
        doc.add(new TextField("name", "vivo，X23。 8GB+128GB； 幻夜蓝", Field.Store.YES));
        indexWriter.addDocument(doc);

        // 6.释放资源
        indexWriter.close();
    }

    /**
     * 使用第三方分词器(IK分词)
     * 特点: 支持中文语义分析, 提供停用词典, 提供扩展词典, 供程序员扩展使用
     * @throws Exception
     */
    @Test
    public void TestIKAnalyzer() throws Exception{
        // 1. 创建分词器,分析文档，对文档进行分词
        Analyzer analyzer = new IKAnalyzer();

        // 2. 创建Directory对象,声明索引库的位置
        Directory directory = FSDirectory.open(Paths.get("E:\\dir"));

        // 3. 创建IndexWriteConfig对象，写入索引需要的配置
        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        // 4.创建IndexWriter写入对象
        IndexWriter indexWriter = new IndexWriter(directory, config);

        // 5.写入到索引库，通过IndexWriter添加文档对象document
        Document doc = new Document();
        doc.add(new TextField("name", "vivo X23 8GB+128GB 幻夜蓝,水滴屏全面屏,游戏手机.移动联通电信全网通4G手机", Field.Store.YES));
        indexWriter.addDocument(doc);

        // 6.释放资源
        indexWriter.close();

    }
}
