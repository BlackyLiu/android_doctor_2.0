package com.base.studentManager;

import com.base.bean.MChat;
import com.base.bean.MClass;
import com.base.bean.MCourse;
import com.base.bean.ScoreUser;
import com.base.bean.User;
import com.base.dao.ChatDao;
import com.base.util.TimeUtils;

import org.junit.Test;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public  void testAddClass(){
        long teacherId =2;
        String name = "科技五班";
        String quBl = "0.1";
        String res = ClassDao.addClass(teacherId, name, quBl);
        System.out.println(res);
    }


    @Test
    public  void testTimer(){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(1);
            }
        },0,1000);
    }

    @Test
    public  void testFunTime(){
        long time = new Date().getTime();
        String msgFormatTime = TimeUtils.getMsgFormatTime(time);
        System.out.println(msgFormatTime);
    }

    @Test
    public  void queryMyTotalScore(){
        ScoreUser scoreUsers = ScoreDao.queryMyTotalScore(13);
        System.out.println(scoreUsers);
    }

    @Test
    public  void queryMyAllQuScore(){
        List<ScoreUser> scoreUsers = ScoreDao.queryMyAllCourseScore(13);
        System.out.println(scoreUsers);
    }

    @Test
    public  void queryAllData(){
        List<MCourse> mCourses = CourseDao.queryAllData();
        System.out.println(mCourses);
    }

    @Test
    public  void queryAllStuByCId(){
        List<User> users = ClassDao.queryAllStuByCId(2);
        System.out.println(users);
    }

    @Test
    public  void queryAllClassByTearcher(){
        List<MClass> mClasses = ClassDao.queryAllClassByTearcher(2);
        System.out.println(mClasses);
    }

    @Test
    public  void queryYxQb(){
//        List<MClass> mClasses = ClassDao.queryAllData();
//        System.out.println(mClasses);
        List<MChat> mChats = ChatDao.queryYxQb(1,2);
//        System.out.println(mChats);
    }


    @Test
    public  void queryLxr(){
//        List<MClass> mClasses = ClassDao.queryAllData();
//        System.out.println(mClasses);
//        List<MChat> mChats = ChatDao.queryYxQb(1,2);
//        System.out.println(mChats);
        List<User> users = ChatDao.queryLxr(2);


    }


    @Test
    public  void testQueryTxYh(){
//        List<MClass> mClasses = ClassDao.queryAllData();
//        System.out.println(mClasses);
        List<User> mChats = ChatDao.queryLxr(2);
    }

    @Test
    public  void testClass(){
        List<MClass> mClasses = ClassDao.queryAllData();
        System.out.println(mClasses);
    }

    @Test
    public  void testLogin(){
        String username = "test";
        String password = "111";
        boolean login = UserDao.login(username, password);
        System.out.println(login);
    }

    @Test
    public  void testUsers(){
//        System.out.println(11);
        List<User> users = UserDao.getUsers();
        System.out.println(users);
    }


}