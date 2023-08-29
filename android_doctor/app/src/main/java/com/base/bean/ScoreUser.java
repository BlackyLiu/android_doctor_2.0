package com.base.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class ScoreUser implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;

    private Long courseId;

    private BigDecimal score;
    //课程总成绩
    private BigDecimal cts;
    //量化总成绩
    private BigDecimal lts;
    //量化比例
    private BigDecimal qu_bl;

    //总成绩
    private BigDecimal sumScore;

    private Long qId;

    private Long stuId;

    private String username;

    private String password;

    private String realName;

    private Long classId;

    private String role;

    private String qu_name;

    private String qu_msg;

    private  String course_name;

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getSumScore() {
        return sumScore;
    }

    public void setSumScore(BigDecimal sumScore) {
        this.sumScore = sumScore;
    }

    public BigDecimal getCts() {
        return cts;
    }

    public void setCts(BigDecimal cts) {
        this.cts = cts;
    }

    public BigDecimal getLts() {
        return lts;
    }

    public void setLts(BigDecimal lts) {
        this.lts = lts;
    }

    public BigDecimal getQu_bl() {
        return qu_bl;
    }

    public void setQu_bl(BigDecimal qu_bl) {
        this.qu_bl = qu_bl;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Long getqId() {
        return qId;
    }

    public void setqId(Long qId) {
        this.qId = qId;
    }

    public Long getStuId() {
        return stuId;
    }

    public void setStuId(Long stuId) {
        this.stuId = stuId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Long getClassId() {
        return classId;
    }

    public void setClassId(Long classId) {
        this.classId = classId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getQu_name() {
        return qu_name;
    }

    public void setQu_name(String qu_name) {
        this.qu_name = qu_name;
    }

    public String getQu_msg() {
        return qu_msg;
    }

    public void setQu_msg(String qu_msg) {
        this.qu_msg = qu_msg;
    }
}
