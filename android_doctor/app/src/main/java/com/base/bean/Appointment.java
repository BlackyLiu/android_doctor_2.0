package com.base.bean;

import java.io.Serializable;
import java.util.Date;


public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * patientid
     */
    private Long appointmentId;

    /**
     * 预约doctorid
     */
    private Long beAppointmentId;

    /**
     * patientrealName
     */
    private String appointmentName;

    /**
     * 预约doctor
     */
    private String beAppointmentName;

    /**
     * Medical record
     */
    private String medicalRecord;

    /**
     * 处方
     */
    private String prescription;

    /**
     * Reservation status
     */
    private String status;

    /**
     * 预约时间
     */
    private Date appointmentTime;

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

    public Long getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Long appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Long getBeAppointmentId() {
        return beAppointmentId;
    }

    public void setBeAppointmentId(Long beAppointmentId) {
        this.beAppointmentId = beAppointmentId;
    }

    public String getAppointmentName() {
        return appointmentName;
    }

    public void setAppointmentName(String appointmentName) {
        this.appointmentName = appointmentName;
    }

    public String getBeAppointmentName() {
        return beAppointmentName;
    }

    public void setBeAppointmentName(String beAppointmentName) {
        this.beAppointmentName = beAppointmentName;
    }

    public String getMedicalRecord() {
        return medicalRecord;
    }

    public void setMedicalRecord(String medicalRecord) {
        this.medicalRecord = medicalRecord;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getAppointmentTime() {
        return appointmentTime;
    }

    public void setAppointmentTime(Date appointmentTime) {
        this.appointmentTime = appointmentTime;
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
