package com.luomo.springbootossminio.domain;

import com.luomo.springbootossminio.impl.Strategy;
import io.minio.errors.*;
import org.springframework.web.multipart.MultipartFile;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class CloudClientMaster implements Strategy {
    public Integer status;
    private Strategy strategy;
    public Strategy getStrategy(){
        return strategy;
    }
    public void setStrategy(Strategy strategy){
        this.strategy = strategy;
    }
    public CloudClientMaster(int status,String url,String accesskey,String screatkey) throws InvalidPortException, InvalidEndpointException {
        this.status = status;
        if(this.status == 1){
            Strategy s = new MinioClientSlave(url,accesskey,screatkey);
            this.setStrategy(s);
        }else{
            Strategy s = new OssClientSlave(url,accesskey,screatkey);
            this.setStrategy(s);
        }
    }
    @Override
    public String upload(MultipartFile file, String bucketName, String objectName,String storeDir) throws IOException, XmlPullParserException, NoSuchAlgorithmException, RegionConflictException, InvalidKeyException, InvalidResponseException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        return strategy.upload(file,bucketName,objectName,storeDir);
    }
    @Override
    public boolean download(String bucketName, String objectName, String filePath,String storeDir) throws IOException, XmlPullParserException, NoSuchAlgorithmException, InvalidKeyException, InvalidArgumentException, InvalidResponseException, ErrorResponseException, NoResponseException, InvalidBucketNameException, InsufficientDataException, InternalException {
        return strategy.download(bucketName,objectName,filePath,storeDir);
    }


}
