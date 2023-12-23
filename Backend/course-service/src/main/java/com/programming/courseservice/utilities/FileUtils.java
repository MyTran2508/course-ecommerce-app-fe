package com.programming.courseservice.utilities;

import com.programming.courseservice.utilities.constant.StorageConstrant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;

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

    public boolean isFileDocument(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(StorageConstrant.EXTENTIONS_DOCUMENT).contains(extension);
    }

    public boolean isFileVideo(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(StorageConstrant.EXTENSIONS_VIDEO).contains(extension);
    }

    public boolean isFileImage(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(StorageConstrant.EXTENSIONS_IMAGE).contains(extension);
    }

    public String uploadFileToSystem(MultipartFile file, Path filePath) {
        try {
            File folder = filePath.getParent().toFile();
            if (!folder.exists()) {
                org.apache.commons.io.FileUtils.forceMkdir(folder);
            }

            file.transferTo(filePath.toFile());
            return filePath.toString().replace("\\", "/");
        } catch (IOException e) {
            return "";
        }
    }
}
