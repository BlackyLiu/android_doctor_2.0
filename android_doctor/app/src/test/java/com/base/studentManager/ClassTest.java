package com.base.studentManager;

import org.junit.Test;

public class ClassTest {

    @Test
    public void delete(){
        String delete = ClassDao.delete(2);
        System.out.println(delete);
    }
}
