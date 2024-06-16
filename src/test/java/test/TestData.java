package test;
/*
Date: 5/26/2024
Author: lu0qlng
Description: 测试Data类
*/

import com.pbrs.mybatis.pojo.Data;
import org.junit.Test;


public class TestData {
    Data data = new Data();
    @Test
    public void testFillNull(){
        data.setAuthor("alsdjsal");
        data.fillNone();
        System.out.println(data);
    }

}
