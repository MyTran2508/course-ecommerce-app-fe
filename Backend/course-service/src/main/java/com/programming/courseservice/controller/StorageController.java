package com.programming.courseservice.controller;

import com.programming.courseservice.service.StorageS3Service;
import com.programming.courseservice.utilities.constant.CourseConstrant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/courses/store")
public class StorageController {

    @Autowired
    private StorageS3Service storageS3;

    @PostMapping("/upload")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> uploadFile(@RequestParam("file")MultipartFile file) {
        return new ResponseEntity<>(storageS3.uploadFile(CourseConstrant.S3Constrant.PATH_COURSE_LECTURE, file), HttpStatus.OK);
    }

    @GetMapping("/download/{fileName}")
    public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
        byte[] data = storageS3.downloadFile(CourseConstrant.S3Constrant.PATH_COURSE_LECTURE + fileName);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentLength(data.length)
                .header("Content-type", "application/octet-stream")
                .header("Content-disposition", "attachment; fileName=\"" + fileName + "\"")
                .body(resource);
    }

    @DeleteMapping("/delete/{fileName}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<String> deleteFile(@PathVariable String fileName) {
        return new ResponseEntity<>(storageS3.deleteFile(CourseConstrant.S3Constrant.PATH_COURSE_LECTURE + fileName), HttpStatus.OK);
    }
}
