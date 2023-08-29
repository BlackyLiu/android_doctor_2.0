package com.base;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.rules.DateType;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        generate();
    }

    //默认生成全部表
    private static void generate() {
        //String path ="";
        String projectPath = "D:\\res\\project\\android\\doctor\\back_doctor";
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/android_doctor?useUnicode=true&characterEncoding=UTF-8&useSSL=false" +
                "&serverTimezone=UTC&serverTimezone=GMT%2b8", "root", "123456")
                .globalConfig(builder -> {
                    GlobalConfig gc = builder.author("admin") // 设置作者
                            //.enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .disableOpenDir() //关闭自动打开文件夹
                            .dateType(DateType.ONLY_DATE)
                            .outputDir(projectPath + "\\src\\main\\java\\").build();// 指定输出目录

                })
                .packageConfig(builder -> {
                    builder.parent("com.base") // 设置父包名
                            .moduleName(null) // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    projectPath+ "\\src\\main\\resources\\mapper\\")); // 设置mapperXml生成路径
                    builder.entity("bean");
                    PackageConfig build = builder.build();
                })
                //.packageConfig(builder -> {
                //    builder.parent("com.base") // 设置父包名
                //            .moduleName(null) // 设置父包模块名
                //            .pathInfo(Collections.singletonMap(OutputFile.entity,
                //                    projectPath+ "\\src\\main\\java\\"+builder.)); // 设置mapperXml生成路径
                //})
                .strategyConfig(builder -> {
                    ////lobok插件
                    builder.entityBuilder().enableLombok();
                    // //加 mapper 注解
//                    builder.mapperBuilder().enableMapperAnnotation().build();
                    builder.controllerBuilder().enableHyphenStyle()  // 开启驼峰转连字符
                            .enableRestStyle();  // 开启生成@RestController 控制器
                    builder.mapperBuilder().enableBaseResultMap().enableBaseColumnList();
                    StrategyConfig build = builder.build();
                    builder.addInclude("doctor") ;// 设置需要生成表名
                    //builder.addInclude("physical_user_view") ;// 设置需要生成表名
                    //builder.addInclude("race") ;// 设置需要生成表名
                    //        .addTablePrefix("t_", "sys_"); // 设置过滤表前缀的
                }).templateConfig(builder->{
                      //myBatis plus 去除生成 controller
                    //TemplateConfig build = builder.build();
                    //build.disable(TemplateType.SERVICE);
                    //build.disable(TemplateType.SERVICEIMPL);
                    //builder.disable(TemplateType.ENTITY);
                    //builder.disable(TemplateType.CONTROLLER);
                    //builder.disable(TemplateType.MAPPER);
                    //builder.disable(TemplateType.XML);
                })
               //.templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();

        //if(!"a".contains("view")){
        //
        //}
    }
}

