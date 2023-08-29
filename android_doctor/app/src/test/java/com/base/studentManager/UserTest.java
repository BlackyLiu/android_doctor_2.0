package com.base.studentManager;

import com.base.bean.User;

import org.junit.Test;

import java.util.List;

public class UserTest {

@Test
     public void testQueryBjYh(){
         List<User> users = UserDao.queryBjYh(18);
         System.out.println(users);
     }
}
