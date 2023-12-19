package com.programming.courseservice.service;

import lombok.RequiredArgsConstructor;
import org.apache.commons.io.FileUtils;
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
    private String COURSE_IMAGE_PATH = "./data-app/course/images/";
    private String COURSE_VIDEO_PATH = "./data-app/course/videos/";
    private String[] validExtensionsImage = {".jpg", ".jpeg", ".png", ".gif"};
    private String[] validExtensionsVideo = {".avi", ".mp4", ".mov"};

    public boolean isFileImage(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(validExtensionsImage).contains(extension);
    }

    private boolean isFileVideo(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(validExtensionsVideo).contains(extension);
    }

    @SuppressWarnings("unchecked")
    public String uploadImageToFileSystem(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(COURSE_IMAGE_PATH).resolve(fileName).toAbsolutePath();

        try {
            if (!isFileImage(file)) {
                return "";
            }

            File folder = filePath.getParent().toFile();
            if (!folder.exists()) {
                FileUtils.forceMkdir(folder);
            }

            file.transferTo(filePath.toFile());
            return filePath.toString().replace("\\", "/");
        } catch (IOException e) {
            return "";
        }
    }

    @SuppressWarnings("unchecked")
    public String uploadVideoToFileSystem(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        Path filePath = Paths.get(COURSE_VIDEO_PATH).resolve(fileName).toAbsolutePath();

        try {
            if (!isFileVideo(file)) {
                return "";
            }

            File folder = filePath.getParent().toFile();
            if (!folder.exists()) {
                FileUtils.forceMkdir(folder);
            }

            file.transferTo(filePath.toFile());
            return filePath.toString().replace("\\", "/");
        } catch (IOException e) {
            return "";
        }
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
