package com.programming.courseservice.controller;

import com.programming.courseservice.util.StorageS3;
import com.programming.courseservice.util.constant.S3Constrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/courses")
public class StorageController {
    @Autowired
    private StorageS3 storageS3;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        return new ResponseEntity<>(storageS3.uploadFile(S3Constrant.pathFolderLecture, file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageS3.downloadFile(S3Constrant.pathFolderLecture + fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; fileName=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageS3.deleteFile(S3Constrant.pathFolderLecture + fileName), HttpStatus.OK);
    }
}
