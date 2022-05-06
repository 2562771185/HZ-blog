package com.jhzz.jhzzblog.controller;

import com.jhzz.jhzzblog.utils.QiniuUtils;
import com.jhzz.jhzzblog.vo.commons.CommonResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/27
 * \* Time: 14:31
 * \* Description:
 * \
 */
@RestController
@RequestMapping("jhzzblog/upload")
public class UploadController {
    @Autowired
    private QiniuUtils qiniuUtils;

    @RequestMapping
    public CommonResult upload(@RequestParam("image") MultipartFile file){
        //获取文件原始名称：a.png a.txt
//        唯一文件名称
        String filename = UUID.randomUUID().toString() +"."+
                //截取.之后      a.txt --> txt
                StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
        boolean upload = qiniuUtils.upload(file, filename);
        if (upload){
            return CommonResult.success(QiniuUtils.url + filename);
        }
        return CommonResult.fail(20001,"上传失败");
    }


}
