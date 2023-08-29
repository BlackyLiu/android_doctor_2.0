package com.base.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import com.base.transfer.ResultBean;
import com.base.transfer.PageTo;
import com.base.service.IAppointmentService;
import com.base.bean.Appointment;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 预约 模块
 * </p>
 */
@RestController
@RequestMapping("appointment")
@Slf4j
public class AppointmentController {
    @Autowired
    private IAppointmentService appointmentService;

    // 新增
    @PostMapping("/add")
    public ResultBean add(Appointment appointment) {
        //appointment.setAppointmentTime(new Date());
        appointment.setCreateTime(new Date());
        appointment.setUpdateTime(new Date());
        boolean save = appointmentService.save(appointment);
        log.info("调用创建接口:{},数据:{}", save, appointment);
        return save ? ResultBean.ok() : ResultBean.error400();
    }

    // 更新
    @PostMapping("/update")
    public ResultBean update(Appointment appointment) {
        appointment.setUpdateTime(new Date());
        boolean update = appointmentService.updateById(appointment);
        log.info("调用更新接口:{},数据:{}", update, appointment);
        return update ? ResultBean.ok() : ResultBean.error400();
    }

    //删除
    @GetMapping("delete")
    @ResponseBody
    public ResultBean delete(Long id) {
        boolean del = appointmentService.removeById(id);
        log.info("调用删除接口:{},数据:{}", del, id);
        return del ? ResultBean.ok() : ResultBean.error400();
    }

    //多项删除
    @GetMapping("batchDelete")
    @ResponseBody
    public ResultBean deleteBatch(String ids) {
        String[] array = ids.split(",");
        boolean del = appointmentService.removeByIds(Arrays.asList(array));
        log.info("调用批量删除接口:{},数据:{}", del, ids);
        return del ? ResultBean.ok() : ResultBean.error400();
    }


    //查询所有数据
    @GetMapping("/queryAllData")
    public ResultBean queryAllData() {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Appointment> appointments = appointmentService.list(queryWrapper);
        return ResultBean.ok(appointments);
    }

    //根据id查询
    @GetMapping("/queryOne")
    public ResultBean queryOne(Long id) {
        Appointment appointment = appointmentService.getById(id);
        return ResultBean.ok(appointment);
    }


    //根据id查询
    @GetMapping("/queryCondition")
    public ResultBean queryCondition(Appointment appointment) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        if (appointment != null) {
            if (appointment.getId() != null) {
                queryWrapper.eq("id", appointment.getId());
            }
            if (appointment.getAppointmentId() != null) {
                queryWrapper.eq("appointment_id", appointment.getAppointmentId());
            }
            if (appointment.getBeAppointmentId() != null) {
                queryWrapper.eq("be_appointment_id", appointment.getBeAppointmentId());
            }
            if (appointment.getAppointmentName() != null) {
                queryWrapper.like("appointment_name", appointment.getAppointmentName());
            }
            if (appointment.getBeAppointmentName() != null) {
                queryWrapper.like("be_appointment_name", appointment.getBeAppointmentName());
            }
            if (appointment.getMedicalRecord() != null) {
                queryWrapper.like("medical_record", appointment.getMedicalRecord());
            }
            if (appointment.getPrescription() != null) {
                queryWrapper.like("prescription", appointment.getPrescription());
            }
            if (appointment.getStatus() != null) {
                queryWrapper.like("status", appointment.getStatus());
            }
        }
        queryWrapper.orderByDesc("create_time");
        List<Appointment> appointments = appointmentService.list(queryWrapper);
        return ResultBean.ok(appointments);
    }

    @GetMapping("/countCondition")
    public ResultBean countCondition(Appointment appointment) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        if (appointment != null) {
            if (appointment.getId() != null) {
                queryWrapper.eq("id", appointment.getId());
            }
            if (appointment.getAppointmentId() != null) {
                queryWrapper.eq("appointment_id", appointment.getAppointmentId());
            }
            if (appointment.getBeAppointmentId() != null) {
                queryWrapper.eq("be_appointment_id", appointment.getBeAppointmentId());
            }
            if (appointment.getAppointmentName() != null) {
                queryWrapper.like("appointment_name", appointment.getAppointmentName());
            }
            if (appointment.getBeAppointmentName() != null) {
                queryWrapper.like("be_appointment_name", appointment.getBeAppointmentName());
            }
            if (appointment.getMedicalRecord() != null) {
                queryWrapper.like("medical_record", appointment.getMedicalRecord());
            }
            if (appointment.getPrescription() != null) {
                queryWrapper.like("prescription", appointment.getPrescription());
            }
            if (appointment.getStatus() != null) {
                queryWrapper.like("status", appointment.getStatus());
            }
        }
        int c = appointmentService.count(queryWrapper);
        return ResultBean.ok(c);
    }


    //分页查询
    @PostMapping("/page")
    public ResultBean page(PageTo<Appointment> pageTo) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        Appointment appointment = pageTo.getObj();
        if (appointment != null) {
            if (appointment.getId() != null) {
                queryWrapper.eq("id", appointment.getId());
            }
            if (appointment.getAppointmentId() != null) {
                queryWrapper.eq("appointment_id", appointment.getAppointmentId());
            }
            if (appointment.getBeAppointmentId() != null) {
                queryWrapper.eq("be_appointment_id", appointment.getBeAppointmentId());
            }
            if (appointment.getAppointmentName() != null) {
                queryWrapper.like("appointment_name", appointment.getAppointmentName());
            }
            if (appointment.getBeAppointmentName() != null) {
                queryWrapper.like("be_appointment_name", appointment.getBeAppointmentName());
            }
            if (appointment.getMedicalRecord() != null) {
                queryWrapper.like("medical_record", appointment.getMedicalRecord());
            }
            if (appointment.getPrescription() != null) {
                queryWrapper.like("prescription", appointment.getPrescription());
            }
            if (appointment.getStatus() != null) {
                queryWrapper.like("status", appointment.getStatus());
            }
        }
        queryWrapper.orderByDesc("create_time");
        Page<Appointment> p = appointmentService.page(new Page<>(pageTo.getPage(), pageTo.getSize()), queryWrapper);
        return ResultBean.ok(p);
    }

    @PostMapping("queryDoctorApp")
    public ResultBean queryDoctorApp(Long id) {
        QueryWrapper<Appointment> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("appointment_id","be_appointment_id");
        queryWrapper.eq("be_appointment_id",id);
        queryWrapper.notIn("status","End of appointment","Reservation failed");
        queryWrapper.orderByDesc("create_time");
        List<Appointment> list = appointmentService.list(queryWrapper);
        return ResultBean.ok(list);
    }
}

