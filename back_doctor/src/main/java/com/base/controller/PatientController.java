package com.base.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.base.bean.Doctor;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.extern.slf4j.Slf4j;
import com.base.transfer.ResultBean;
import com.base.transfer.PageTo;
import com.base.service.IPatientService;
import com.base.bean.Patient;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户 模块
 * </p>
 */
@RestController
@RequestMapping("patient")
@Slf4j
public class PatientController {
    @Autowired
    private IPatientService patientService;

    // 新增
    @PostMapping("/add")
    public ResultBean add(Patient patient) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        //queryWrapper.eq("real_name",patient.getRealName());
        queryWrapper.eq("email",patient.getEmail());
        int count = patientService.count(queryWrapper);
        if(count>0){
            return  ResultBean.error400("邮箱已经病人已经存在");
        }


        patient.setCreateTime(new Date());
        patient.setUpdateTime(new Date());
        boolean save = patientService.save(patient);
        log.info("调用创建接口:{},数据:{}", save, patient);
        return save ? ResultBean.ok() : ResultBean.error400();
    }

    @PostMapping("/reg")
    public ResultBean reg(Patient patient) {
        long l = System.currentTimeMillis();
        patient.setMedicalTreatmentNo(String.valueOf(l));
        patient.setCreateTime(new Date());
        patient.setUpdateTime(new Date());
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email",patient.getEmail());
        List<Patient> list = patientService.list(queryWrapper);
        if(list.size() >0 ){
            return  ResultBean.error400();
        }
        boolean save = patientService.save(patient);
        log.info("调用创建接口:{},数据:{}", save, patient);
        return save ? ResultBean.ok() : ResultBean.error400();
    }

    // 更新
    @PostMapping("/update")
    public ResultBean update(Patient patient) {
        patient.setUpdateTime(new Date());
        boolean update = patientService.updateById(patient);
        log.info("调用更新接口:{},数据:{}", update, patient);
        return update ? ResultBean.ok() : ResultBean.error400();
    }

    //删除
    @GetMapping("delete")
    @ResponseBody
    public ResultBean delete(Long id) {
        boolean del = patientService.removeById(id);
        log.info("调用删除接口:{},数据:{}", del, id);
        return del ? ResultBean.ok() : ResultBean.error400();
    }

    //多项删除
    @GetMapping("batchDelete")
    @ResponseBody
    public ResultBean deleteBatch(String ids) {
        String[] array = ids.split(",");
        boolean del = patientService.removeByIds(Arrays.asList(array));
        log.info("调用批量删除接口:{},数据:{}", del, ids);
        return del ? ResultBean.ok() : ResultBean.error400();
    }


    //查询所有数据
    @GetMapping("/queryAllData")
    public ResultBean queryAllData() {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        List<Patient> patients = patientService.list(queryWrapper);
        return ResultBean.ok(patients);
    }

    //根据id查询
    @GetMapping("/queryOne")
    public ResultBean queryOne(Long id) {
        Patient patient = patientService.getById(id);
        return ResultBean.ok(patient);
    }


    //根据id查询
    @GetMapping("/queryCondition")
    public ResultBean queryCondition(Patient patient) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        if (patient != null) {
            if (patient.getId() != null) {
                queryWrapper.eq("id", patient.getId());
            }
            if (patient.getMedicalTreatmentNo() != null) {
                queryWrapper.like("medical_treatment_no", patient.getMedicalTreatmentNo());
            }
            if (patient.getEmail() != null) {
                queryWrapper.eq("email", patient.getEmail());
            }
            if (patient.getPassword() != null) {
                queryWrapper.eq("password", patient.getPassword());
            }
            if (patient.getRealName() != null) {
                queryWrapper.like("real_name", patient.getRealName());
            }
            if (patient.getPrice() != null) {
                queryWrapper.eq("price", patient.getPrice());
            }
            if (patient.getContactInformation() != null) {
                queryWrapper.like("contact_information", patient.getContactInformation());
            }
            if (patient.getBirthday() != null) {
                queryWrapper.like("birthday", patient.getBirthday());
            }
        }
        queryWrapper.orderByDesc("create_time");
        List<Patient> patients = patientService.list(queryWrapper);
        return ResultBean.ok(patients);
    }

    @GetMapping("/countCondition")
    public ResultBean countCondition(Patient patient) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        if (patient != null) {
            if (patient.getId() != null) {
                queryWrapper.eq("id", patient.getId());
            }
            if (patient.getMedicalTreatmentNo() != null) {
                queryWrapper.like("medical_treatment_no", patient.getMedicalTreatmentNo());
            }
            if (patient.getEmail() != null) {
                queryWrapper.like("email", patient.getEmail());
            }
            if (patient.getPassword() != null) {
                queryWrapper.like("password", patient.getPassword());
            }
            if (patient.getRealName() != null) {
                queryWrapper.like("real_name", patient.getRealName());
            }
            if (patient.getPrice() != null) {
                queryWrapper.eq("price", patient.getPrice());
            }
            if (patient.getContactInformation() != null) {
                queryWrapper.like("contact_information", patient.getContactInformation());
            }
            if (patient.getBirthday() != null) {
                queryWrapper.like("birthday", patient.getBirthday());
            }
        }
        int c = patientService.count(queryWrapper);
        return ResultBean.ok(c);
    }


    //分页查询
    @PostMapping("/page")
    public ResultBean page(PageTo<Patient> pageTo) {
        QueryWrapper<Patient> queryWrapper = new QueryWrapper<>();
        Patient patient = pageTo.getObj();
        if (patient != null) {
            if (patient.getId() != null) {
                queryWrapper.eq("id", patient.getId());
            }
            if (patient.getMedicalTreatmentNo() != null) {
                queryWrapper.like("medical_treatment_no", patient.getMedicalTreatmentNo());
            }
            if (patient.getEmail() != null) {
                queryWrapper.like("email", patient.getEmail());
            }
            if (patient.getPassword() != null) {
                queryWrapper.like("password", patient.getPassword());
            }
            if (patient.getRealName() != null) {
                queryWrapper.like("real_name", patient.getRealName());
            }
            if (patient.getPrice() != null) {
                queryWrapper.eq("price", patient.getPrice());
            }
            if (patient.getContactInformation() != null) {
                queryWrapper.like("contact_information", patient.getContactInformation());
            }
            if (patient.getBirthday() != null) {
                queryWrapper.like("birthday", patient.getBirthday());
            }
        }
        queryWrapper.orderByDesc("create_time");
        Page<Patient> p = patientService.page(new Page<>(pageTo.getPage(), pageTo.getSize()), queryWrapper);
        return ResultBean.ok(p);
    }
}

