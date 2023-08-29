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
import com.base.service.IDoctorService;
import com.base.bean.Doctor;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 模块
 * </p>
 */
@RestController
@RequestMapping("doctor")
@Slf4j
public class DoctorController {
    @Autowired
    private IDoctorService doctorService;

    // 新增
    @PostMapping("/add")
    public ResultBean add(Doctor doctor) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("real_name",doctor.getRealName());
        queryWrapper.or().eq("email",doctor.getEmail());
        int count = doctorService.count(queryWrapper);
        if(count>0){
            return  ResultBean.error400("邮箱和医生名字不能重复");
        }

        long l = System.currentTimeMillis();
        String s = String.valueOf(l);
        doctor.setJobNumber(s);
        doctor.setCreateTime(new Date());
        doctor.setUpdateTime(new Date());
        boolean save = doctorService.save(doctor);
        log.info("调用创建接口:{},数据:{}", save, doctor);
        return save ? ResultBean.ok() : ResultBean.error400();
    }

    // 更新
    @PostMapping("/update")
    public ResultBean update(Doctor doctor) {
        doctor.setUpdateTime(new Date());
        boolean update = doctorService.updateById(doctor);
        log.info("调用更新接口:{},数据:{}", update, doctor);
        return update ? ResultBean.ok() : ResultBean.error400();
    }

    //删除
    @GetMapping("delete")
    @ResponseBody
    public ResultBean delete(Long id) {
        boolean del = doctorService.removeById(id);
        log.info("调用删除接口:{},数据:{}", del, id);
        return del ? ResultBean.ok() : ResultBean.error400();
    }

    //多项删除
    @GetMapping("batchDelete")
    @ResponseBody
    public ResultBean deleteBatch(String ids) {
        String[] array = ids.split(",");
        boolean del = doctorService.removeByIds(Arrays.asList(array));
        log.info("调用批量删除接口:{},数据:{}", del, ids);
        return del ? ResultBean.ok() : ResultBean.error400();
    }


    //查询所有数据
    @GetMapping("/queryAllData")
    public ResultBean queryAllData() {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Doctor> doctors = doctorService.list(queryWrapper);
        return ResultBean.ok(doctors);
    }

    //根据id查询
    @GetMapping("/queryOne")
    public ResultBean queryOne(Long id) {
        Doctor doctor = doctorService.getById(id);
        return ResultBean.ok(doctor);
    }


    //根据id查询
    @GetMapping("/queryCondition")
    public ResultBean queryCondition(Doctor doctor) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        if (doctor != null) {
            if (doctor.getId() != null) {
                queryWrapper.eq("id", doctor.getId());
            }
            if (doctor.getJobNumber() != null) {
                queryWrapper.like("job_number", doctor.getJobNumber());
            }
            if (doctor.getEmail() != null) {
                queryWrapper.eq("email", doctor.getEmail());
            }
            if (doctor.getPassword() != null) {
                queryWrapper.eq("password", doctor.getPassword());
            }
            if (doctor.getRealName() != null) {
                queryWrapper.like("real_name", doctor.getRealName());
            }
            if (doctor.getContactInformation() != null) {
                queryWrapper.like("contact_information", doctor.getContactInformation());
            }
            if (doctor.getRemark() != null) {
                queryWrapper.like("remark", doctor.getRemark());
            }
            if (doctor.getDepartments() != null) {
                queryWrapper.like("departments", doctor.getDepartments());
            }
            if (doctor.getAppointment() != null) {
                queryWrapper.eq("appointment", doctor.getAppointment());
            }
        }
        queryWrapper.orderByDesc("create_time");
        List<Doctor> doctors = doctorService.list(queryWrapper);
        return ResultBean.ok(doctors);
    }

    @GetMapping("/countCondition")
    public ResultBean countCondition(Doctor doctor) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        if (doctor != null) {
            if (doctor.getId() != null) {
                queryWrapper.eq("id", doctor.getId());
            }
            if (doctor.getJobNumber() != null) {
                queryWrapper.like("job_number", doctor.getJobNumber());
            }
            if (doctor.getEmail() != null) {
                queryWrapper.eq("email", doctor.getEmail());
            }
            if (doctor.getPassword() != null) {
                queryWrapper.eq("password", doctor.getPassword());
            }
            if (doctor.getRealName() != null) {
                queryWrapper.like("real_name", doctor.getRealName());
            }
            if (doctor.getContactInformation() != null) {
                queryWrapper.like("contact_information", doctor.getContactInformation());
            }
            if (doctor.getRemark() != null) {
                queryWrapper.like("remark", doctor.getRemark());
            }
            if (doctor.getDepartments() != null) {
                queryWrapper.like("departments", doctor.getDepartments());
            }
        }
        int c = doctorService.count(queryWrapper);
        return ResultBean.ok(c);
    }


    //分页查询
    @PostMapping("/page")
    public ResultBean page(PageTo<Doctor> pageTo) {
        QueryWrapper<Doctor> queryWrapper = new QueryWrapper<>();
        Doctor doctor = pageTo.getObj();
        if (doctor != null) {
            if (doctor.getId() != null) {
                queryWrapper.eq("id", doctor.getId());
            }
            if (doctor.getJobNumber() != null) {
                queryWrapper.like("job_number", doctor.getJobNumber());
            }
            if (doctor.getEmail() != null) {
                queryWrapper.like("email", doctor.getEmail());
            }
            if (doctor.getPassword() != null) {
                queryWrapper.like("password", doctor.getPassword());
            }
            if (doctor.getRealName() != null) {
                queryWrapper.like("real_name", doctor.getRealName());
            }
            if (doctor.getContactInformation() != null) {
                queryWrapper.like("contact_information", doctor.getContactInformation());
            }
            if (doctor.getRemark() != null) {
                queryWrapper.like("remark", doctor.getRemark());
            }
            if (doctor.getDepartments() != null) {
                queryWrapper.like("departments", doctor.getDepartments());
            }
        }
        queryWrapper.orderByDesc("create_time");
        Page<Doctor> p = doctorService.page(new Page<>(pageTo.getPage(), pageTo.getSize()), queryWrapper);
        return ResultBean.ok(p);
    }


}

