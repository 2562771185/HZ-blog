package com.jhzz.jhzzblog.utils;

import com.alibaba.fastjson.JSON;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.BucketManager;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * \* Created with IntelliJ IDEA.
 * \* @author: Huanzhi
 * \* Date: 2022/4/27
 * \* Time: 15:06
 * \* Description:
 * \
 */
@Component
@Slf4j
public class QiniuUtils {
    public static  final String url = "http://xxxx.hn-bkt.xxxx.com/";

    @Value("${qiniu.accessKey}")
    private  String accessKey;
    @Value("${qiniu.accessSecretKey}")
    private  String accessSecretKey;

    String bucket = "xxx";

    public  boolean upload(MultipartFile file, String fileName){

        //构造一个带指定 Region 对象的配置类
        Configuration cfg = new Configuration(Region.huanan());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        try {
            byte[] uploadBytes = file.getBytes();
            Auth auth = Auth.create(accessKey, accessSecretKey);
            String upToken = auth.uploadToken(bucket);
            Response response = uploadManager.put(uploadBytes, fileName, upToken);
            //解析上传成功的结果
            DefaultPutRet putRet = JSON.parseObject(response.bodyString(), DefaultPutRet.class);
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    /**
     * key为不加域名的在七牛云中的图片的名称
     * 通过key删除七牛云上的图片
     * 如：http://qlwdwvwug.hn-bkt.clouddn.com/33_20212604132639.jpg
     *  key为 33_20212604132639.jpg
     * @param key key为不加域名的在七牛云中的图片的名称
     */
    public void deleteFile(String key){
        //创建凭证
        Auth auth = Auth.create(accessKey, accessSecretKey);
        BucketManager bucketManager = new BucketManager(auth, new Configuration());
        try {
            bucketManager.delete(bucket, key);
            log.info("---删除为发布的文章图片:{}",key);
        } catch (QiniuException ex) {
            //如果遇到异常，说明删除失败
            System.err.println(ex.code());
            System.err.println(ex.response.toString());
        }
    }
}
