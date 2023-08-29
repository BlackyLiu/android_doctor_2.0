package com;

import com.base.bean.ScoreUser;

import org.junit.Test;

import java.util.List;

public class QuTest {

    @Test
    public  void testDelete(){
        String res = QuantificationDao.delete(3);
        System.out.println(res);
    }


    @Test
      public  void testAddQuan(){
          String res = QuantificationDao.addQuan(13, "量化4", "测试量化", 200);
          System.out.println(res);
      }

    @Test
    public  void testQueryQuAllScore(){
        List<ScoreUser> scoreUsers = QuantificationDao.queryQuAllScore(3);
        System.out.println(scoreUsers);
    }
}
