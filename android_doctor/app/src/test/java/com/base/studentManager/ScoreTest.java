package com.base.studentManager;

import com.base.bean.ScoreUser;

import org.junit.Test;

import java.util.List;

public class ScoreTest {

    @Test
    public void testQueryAllTotalScore(){
        List<ScoreUser> scoreUsers = ScoreDao.queryAllTotalScore(2l);
        System.out.println(scoreUsers.size());
    }
}
