package com.company.vueblog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSClientBuilder;
import com.company.vueblog.service.IOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/9
 */
@Service
public class OssServiceImpl implements IOssService {

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;


    @Value("${aliyun.oss.endpoint}")
    String endPoint;

    @Value("${aliyun.oss.accessKeyId}")
    String accessKeyId;

    @Value("${aliyun.oss.accessKeySecret}")
    String accessKeySecret;


    @Override
    public String upload(MultipartFile file) {
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);
        //获取文件名称
        String filename = file.getOriginalFilename();

        //由于阿里云OSS对于相同文件名称会进行覆盖,所以我们要对文件名称进行处理:前面加上一个随机数
        //1.在文件名称里面添加随机唯一的值
        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
        filename = uuid+filename;
        //2.把文件按照日期进行分类
        //2022/2/20/a.jpg
        String datePath = new DateTime().toString("yyyy/MM/dd");
        filename = datePath+ "/" +filename;

        try {
            InputStream inputStream = file.getInputStream();
            // 发送PutObject请求,进行文件上传
            //bucketName: Bucket名称
            //filename: 上传到OSS的文件路径和文件名称 如 /aa/bb/1.jpg
            //inputStream: 文件输入流
            ossClient.putObject(bucketName, filename, inputStream);

            //https://guli-photos.oss-cn-hangzhou.aliyuncs.com/Snipaste_2022-02-10_23-34-41.jpg
            String url = "https://"+bucketName+"."+endPoint+"/"+filename;
            return url;
            //上传成功,返回url
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }finally {
            //关闭OSSClient
            ossClient.shutdown();
        }
    }
}
