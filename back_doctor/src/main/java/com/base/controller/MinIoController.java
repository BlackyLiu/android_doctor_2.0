package com.base.controller;

import com.alibaba.fastjson2.JSON;
import com.base.to.MinIoUploadResDTO;
import com.base.transfer.ResultBean;
import com.base.util.MinIoUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * Author: hsw
 * Date: 2022/04/12/10:12
 */
@RestController
@RequestMapping("/minio")
@Slf4j
public class MinIoController {

    @Resource
    private MinIoUtils minIoUtils;

    // 存储桶名称
    private static final String MINIO_BUCKET =MinIoUtils.chunkBucKet;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    public ResultBean upload(@RequestParam(value = "file") MultipartFile files,String name){
        log.debug("上传图片"+name);
        try {
            MinIoUploadResDTO upload = minIoUtils.upload(files, MINIO_BUCKET, name);
            return ResultBean.ok(upload);
        } catch (Exception e) {
            return ResultBean.error400(e.getMessage());
        }
    }

    @GetMapping("/download")
    public void download(@RequestParam("minFileName")String minFileName,HttpServletResponse response){
            minIoUtils.download(response,MINIO_BUCKET,minFileName);
    }

    @GetMapping("/list")
    public ResultBean list(String dir){
        log.debug("目录:"+dir);
        //List<String> data = minIoUtils.listBucketNames();
        List<String> data = minIoUtils.listObjectNames(MINIO_BUCKET, dir);
        return  ResultBean.ok(data);
    }

    @GetMapping("/delete")
    public  ResultBean delete(String objectName){
        objectName = replaceName(objectName);
        boolean b = minIoUtils.removeObject(MINIO_BUCKET, objectName);
        return  ResultBean.ok(b);
    }


    @PostMapping("/batchDelete")
    public  ResultBean batchDelete(String ids){
        List<String> data = JSON.parseArray(ids, String.class);
        List<String> collect = data.stream().map(item -> {
            return replaceName(item);
        }).collect(Collectors.toList());
        //boolean b = minIoUtils.removeObject(MINIO_BUCKET, objectName);
        minIoUtils.removeObjects(MINIO_BUCKET,collect);
        return  ResultBean.ok();
    }



    public  String replaceName(String objectName){
        int start = objectName.indexOf(MINIO_BUCKET);
        if(start>=0){
            objectName = objectName.substring(MINIO_BUCKET.length()+1 + start);
        }
        return objectName;
    }


}
