package com.programming.courseservice.utilities;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

@Component
@Slf4j
public class FileUtils {
    private String[] validExtensionsDocument = {".pdf", ".docx", ".doc", ".txt"};
    private String[] validExtensionsLecture = {".avi", ".mp4", ".mov"};
    public File convertMultipartFiletoFile(MultipartFile multipartFile) {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
        return convertedFile;
    }

    public boolean isFileDocument(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(validExtensionsDocument).contains(extension);
    }

    public boolean isFileVideo(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(validExtensionsLecture).contains(extension);
    }
}
