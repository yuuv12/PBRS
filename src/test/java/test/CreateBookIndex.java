package test;
/*
Date: 5/26/2024
Author: lu0qlng
Description: 
*/

import com.pbrs.searchsystem.BookIndex;
import org.junit.Test;

public class CreateBookIndex {
    @Test
    public void CreateBookIndex() throws Exception {
        BookIndex bi = new BookIndex();
        bi.createIndexTest();
    }

}
