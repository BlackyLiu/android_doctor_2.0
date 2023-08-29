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
import com.base.service.IAdminService;
import com.base.bean.Admin;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 管理员 模块
 * </p>
 */
@RestController
@RequestMapping("admin")
@Slf4j
public class AdminController {
        @Autowired
        private IAdminService adminService;

        // 新增
        @PostMapping("/add")
        public ResultBean add( Admin admin) {
                admin.setCreateTime(new Date());
                boolean save =adminService.save(admin);
                log.info("调用创建接口:{},数据:{}",save,admin);
                return save?ResultBean.ok():ResultBean.error400();
        }

        // 更新
        @PostMapping("/update")
        public  ResultBean update( Admin admin){
                boolean update = adminService.updateById(admin);
                log.info("调用更新接口:{},数据:{}",update,admin);
                return update?ResultBean.ok():ResultBean.error400();
        }

        //删除
        @GetMapping("delete")
        @ResponseBody
        public ResultBean delete(Long id) {
                boolean del = adminService.removeById(id);
                log.info("调用删除接口:{},数据:{}",del,id);
                return del?ResultBean.ok():ResultBean.error400();
        }

    //多项删除
        @GetMapping("batchDelete")
        @ResponseBody
        public ResultBean deleteBatch(String ids) {
                String[] array = ids.split(",");
                boolean del = adminService.removeByIds(Arrays.asList(array));
                log.info("调用批量删除接口:{},数据:{}",del,ids);
                return del?ResultBean.ok():ResultBean.error400();
        }


        //查询所有数据
        @GetMapping("/queryAllData")
        public ResultBean queryAllData() {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            queryWrapper.orderByDesc("create_time");
            List<Admin>  admins =  adminService.list(queryWrapper);
            return ResultBean.ok(admins);
        }

        //根据id查询
        @GetMapping("/queryOne")
        public ResultBean queryOne(Long id) {
            Admin  admin =   adminService.getById(id);
            return ResultBean.ok(admin);
        }


        //根据id查询
        @GetMapping("/queryCondition")
        public ResultBean queryCondition( Admin admin) {
             QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
             if( admin!=null){
                    if(admin.getId()!=null){
                        queryWrapper.eq("id",admin.getId());
                    }
                    if(admin.getUsername()!=null){
                         queryWrapper.like("username",admin.getUsername());
                    }
                    if(admin.getPassword()!=null){
                         queryWrapper.like("password",admin.getPassword());
                    }
                 }
                 queryWrapper.orderByDesc("create_time");
                 List<Admin>  admins = adminService.list(queryWrapper);
                 return ResultBean.ok(admins);
        }

        @GetMapping("/countCondition")
        public ResultBean countCondition( Admin admin) {
                QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
                if( admin!=null){
                        if(admin.getId()!=null){
                            queryWrapper.eq("id",admin.getId());
                        }
                        if(admin.getUsername()!=null){
                            queryWrapper.like("username",admin.getUsername());
                        }
                        if(admin.getPassword()!=null){
                            queryWrapper.like("password",admin.getPassword());
                        }
                }
                int c = adminService.count(queryWrapper);
                return ResultBean.ok(c);
        }


        //分页查询
        @PostMapping("/page")
        public ResultBean page( PageTo<Admin> pageTo) {
            QueryWrapper<Admin> queryWrapper = new QueryWrapper<>();
            Admin  admin = pageTo.getObj();
            if( admin!=null){
                    if(admin.getId()!=null){
                         queryWrapper.eq("id",admin.getId());
                    }
                    if(admin.getUsername()!=null){
                         queryWrapper.like("username",admin.getUsername());
                    }
                    if(admin.getPassword()!=null){
                         queryWrapper.like("password",admin.getPassword());
                    }
            }
            queryWrapper.orderByDesc("create_time");
            Page<Admin> p =adminService.page(new Page<>(pageTo.getPage(), pageTo.getSize()), queryWrapper);
            return ResultBean.ok(p);
        }
}

