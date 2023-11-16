package com.programming.courseservice.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.programming.courseservice.util.FileUtils;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

@Service
public class StorageS3Service {
    @Value("${application.bucket.name}")
    private String bucketName;
    @Autowired
    private AmazonS3 s3Client;
    @Autowired
    private FileUtils fileUtils;

//    public Long getVideoDuration(String path) {
//        String s3VideoUrl= "https://" + bucketName + ".s3.ap-southeast-1.amazonaws.com/" + path;
//        String s3VideoUrl2 = UriComponentsBuilder.fromUriString(s3VideoUrl).build().encode().toString();
//        System.out.println(s3VideoUrl2);
//        try {
//            // Sử dụng FFmpegFrameGrabber để lấy thời lượng video từ InputStream
//            try (FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(s3VideoUrl2)) {
//                grabber.start();
//                double duration = grabber.getLengthInTime() / 1000000.0;
//                return (long) duration;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

    public String uploadFile(String pathFolder, MultipartFile multipartFile) {
        File file = fileUtils.convertMultipartFiletoFile(multipartFile);
        String path = pathFolder + System.currentTimeMillis() + "_" + multipartFile.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName, path, file));
        file.delete();
        return path;
    }
    public byte[] downloadFile(String fileName) {
        S3Object s3Object = s3Client.getObject(bucketName, fileName);
        S3ObjectInputStream inputStream = s3Object.getObjectContent();
        try {
            byte[] content = IOUtils.toByteArray(inputStream);
            return content;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String deleteFile(String fileName) {
        s3Client.deleteObject(bucketName, fileName);
        return fileName + "remove";
    }
}
