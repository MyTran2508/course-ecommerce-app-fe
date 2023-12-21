package com.programming.courseservice.service;

import com.programming.courseservice.utilities.FileUtils;
import com.programming.courseservice.utilities.constant.StorageConstrant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final FileUtils fileUtils;

    @SuppressWarnings("unchecked")
    public String uploadImageToFileSystem(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(StorageConstrant.COURSE_IMAGE_PATH).resolve(fileName).toAbsolutePath();

        if (!fileUtils.isFileImage(file)) {
            return "";
        }

        return fileUtils.uploadFileToSystem(file, filePath);
    }

    @SuppressWarnings("unchecked")
    public String uploadVideoToFileSystem(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(StorageConstrant.COURSE_VIDEO_PATH).resolve(fileName).toAbsolutePath();

        if (!fileUtils.isFileVideo(file)) {
            return "";
        }
        return fileUtils.uploadFileToSystem(file, filePath);
    }

    @SuppressWarnings("unchecked")
    public String uploadDocumentToFileSystem(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(StorageConstrant.COURSE_DOCUMENT_PATH).resolve(fileName).toAbsolutePath();

//        if (!fileUtils.isFileDocument(file)) {
//            return "";
//        }
        return fileUtils.uploadFileToSystem(file, filePath);
    }


    public byte[] loadImageFromFileSystem(String filePath) {
        try {
            return Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            return "Not Found".getBytes();
        }
    }

    public void deleteFileFromSystem(String filePath) {
        File file = new File(filePath);
        file.delete();
    }

}
