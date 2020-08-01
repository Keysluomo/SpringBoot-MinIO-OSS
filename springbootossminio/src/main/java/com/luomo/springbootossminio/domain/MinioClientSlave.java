package com.luomo.springbootossminio.domain;
import com.luomo.springbootossminio.impl.Strategy;
import io.minio.MinioClient;
import io.minio.ObjectStat;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

public class MinioClientSlave implements Strategy {
    private String url;
    private String accesskey;
    private String screatkey;

    private MinioClient minioClient;
    public MinioClientSlave (String url,String accesskey,String screatkey) throws InvalidPortException, InvalidEndpointException {
        this.minioClient = new MinioClient(url,accesskey,screatkey);
    }
    @Override
    public String upload(MultipartFile file, String bucketName, String objectName,String storeDir) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, RegionConflictException {
        if (file.isEmpty() || (file.getSize() == 0)) {
            return "文件为空";
        }
        if (!minioClient.bucketExists(bucketName)) {
            minioClient.makeBucket(bucketName);
        }
        String uploadFileTips = "";
        try {
            String fileName = file.getOriginalFilename();
            objectName = storeDir + "/" + objectName;
            InputStream inputStream = file.getInputStream();
            minioClient.putObject(bucketName, objectName, inputStream,"image/jpeg");
            inputStream.close();
            uploadFileTips = objectName + "文件上传成功！";
            return uploadFileTips;
        } catch (Exception e) {
            e.printStackTrace();
            return "文件上传失败";
        }
    }
    @Override
    public boolean download(String bucketName, String objectName, String filePath,String storeDir) throws IOException, InvalidKeyException, NoSuchAlgorithmException, InsufficientDataException, InvalidResponseException, InternalException, NoResponseException, InvalidBucketNameException, XmlPullParserException, ErrorResponseException, InvalidArgumentException {
        objectName = storeDir + "/" + objectName;
        boolean flag = minioClient.bucketExists(bucketName);
        if (flag) {
            ObjectStat statObject = minioClient.statObject(bucketName, objectName);
            if (statObject != null && statObject.length() > 0) {
                minioClient.getObject(bucketName, objectName, filePath);
                return true;
            }
        }
        return false;
    }
}
