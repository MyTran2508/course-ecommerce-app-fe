package com.programming.courseservice.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Component
@Slf4j
public class FileUtils {
    public File convertMultipartFiletoFile(MultipartFile multipartFile) {
        File convertedFile = new File(multipartFile.getOriginalFilename());
        try(FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(multipartFile.getBytes());
        } catch (IOException ioException) {
            log.error(ioException.getMessage());
        }
        return convertedFile;
    }
}
