package com.luomo.springbootossminio.controller;


import com.luomo.springbootossminio.domain.CloudClientMaster;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
@RestController
public class CloudClientController {

    @Value("${cloudclient.status}")
    private Integer status;
    @Value("${cloudclient.url}")
    private String url;
    @Value("${cloudclient.accesskey}")
    private String accesskey;
    @Value("${cloudclient.secretkey}")
    private String secretkey;
    @Value("${cloudclient.bucketName}")
    private String bucketName;
    @Value("${cloudclient.storeDir}")
    private String storeDir;

    /**
     *  上传当前文件
     * @param file
     * @param objectName
     */
    @PostMapping("/upload")
    public String MinIOUpload(@RequestParam MultipartFile file, @RequestParam String objectName) throws InvalidPortException, InvalidEndpointException, IOException, InvalidKeyException, NoSuchAlgorithmException, XmlPullParserException, InvalidResponseException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException, RegionConflictException {
        //System.out.println(1+url+accesskey+secretkey);
        CloudClientMaster cloudClientMaster = new CloudClientMaster(status,url,accesskey,secretkey);
        return cloudClientMaster.upload(file,bucketName,objectName,storeDir);
    }
    /**
     *  下载当前文件
     * @param
     * @param objectName
     */
    @PostMapping("/downloadFile")
    public void MinIOdownload(@RequestParam String objectName,@RequestParam String filePath) throws IOException, InvalidResponseException, InvalidKeyException, NoSuchAlgorithmException, InternalException, InvalidBucketNameException, InsufficientDataException, ErrorResponseException, InvalidPortException, InvalidEndpointException, NoResponseException, XmlPullParserException, InvalidArgumentException {
        CloudClientMaster cloudClientMaster = new CloudClientMaster(status,url,accesskey,secretkey);
        cloudClientMaster.download(bucketName,objectName,filePath,storeDir);
    }
}
