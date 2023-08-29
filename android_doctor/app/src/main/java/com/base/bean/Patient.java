package com.base.bean;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * medicalTreatmentNo
     */
    private String medicalTreatmentNo;

    /**
     * email
     */
    private String email;

    /**
     * password
     */
    private String password;

    /**
     * realName
     */
    private String realName;

    /**
     * 金额
     */
    private BigDecimal price;

    /**
     * 联系信息
     */
    private String contactInformation;

    /**
     * birthday
     */
    private String birthday;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMedicalTreatmentNo() {
        return medicalTreatmentNo;
    }

    public void setMedicalTreatmentNo(String medicalTreatmentNo) {
        this.medicalTreatmentNo = medicalTreatmentNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getContactInformation() {
        return contactInformation;
    }

    public void setContactInformation(String contactInformation) {
        this.contactInformation = contactInformation;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
