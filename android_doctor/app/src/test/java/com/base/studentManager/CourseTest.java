package com.base.studentManager;

import com.base.bean.MCourse;

import org.junit.Test;

import java.util.List;

public class CourseTest {

    @Test
    public void testQueryClassAllCourse(){
        List<MCourse> mCourses = CourseDao.queryClassAllCourse(2);
        System.out.println(mCourses);
    }
}
