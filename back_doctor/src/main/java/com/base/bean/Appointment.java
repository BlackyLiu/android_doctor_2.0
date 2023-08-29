package com.base.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class Appointment implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @TableField(value = "id")
    private Long id;

    /**
     * 预约人id
     */
    @TableField(value = "appointment_id")
    private Long appointmentId;

    /**
     * 预约医生id
     */
    @TableField(value = "be_appointment_id")
    private Long beAppointmentId;

    /**
     * 预约人姓名
     */
    @TableField(value = "appointment_name")
    private String appointmentName;

    /**
     * 预约医生
     */
    @TableField(value = "be_appointment_name")
    private String beAppointmentName;

    /**
     * 就诊记录
     */
    @TableField(value = "medical_record")
    private String medicalRecord;

    /**
     * 处方
     */
    @TableField(value = "prescription")
    private String prescription;

    /**
     * 预约状态
     */
    @TableField(value = "status")
    private String status;

    /**
     * 预约时间
     */
    @TableField(value = "appointment_time")
    private Date appointmentTime;

    /**
     * 创建时间
     */
    @TableField(value = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time")
    private Date updateTime;


}
