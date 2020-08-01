package com.luomo.springbootossminio.domain;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.aliyun.oss.model.PutObjectRequest;

import com.luomo.springbootossminio.impl.Strategy;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.InputStream;
public class OssClientSlave  implements Strategy {
    private String url;
    private String accesskey;
    private String screatkey;
    //private String storeDir;
    private OSSClient ossClient;
    public OssClientSlave (String url,String accesskey,String screatkey){
        this.ossClient = new OSSClient(url,accesskey,screatkey);
    }
    /**
     *
     * @param file：上传的文件
     * @param bucketName：上传的指定bucketName
     * @param objectName：objectName
     * @return
     */
    @Override
    public String upload(MultipartFile file, String bucketName, String objectName,String storeDir) {
        String uploadFileTips = "";
        if(!ossClient.isBucketExist(bucketName)){
            ossClient.createBucket(bucketName);
        }
        try {
            String fileName = file.getOriginalFilename();
            objectName = storeDir + "/" + objectName;
            InputStream inputStream = file.getInputStream();
            /**
             *  bucketName:
             *  如果是指定的bucketName，那就在application.yml中指定出，修改方法中传入参数
             *  如果是根据传入参数，就不需要改动
             */
            ossClient.putObject(new PutObjectRequest(bucketName,objectName,inputStream));
            inputStream.close();
            uploadFileTips = objectName + "文件上传成功！";
            return uploadFileTips;
        } catch (Exception e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }
    /**
     *
     * @param bucketName:这里是我们指定的bucketName
     * @param objectName:存储的objectName
     * @param filePath: 存储到我们本地路径
     * @return
     */
    @Override
    public boolean download(String bucketName, String objectName, String filePath,String storeDir) {
        objectName = storeDir + "/" + objectName;
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(filePath));
        return true;
    }
}

