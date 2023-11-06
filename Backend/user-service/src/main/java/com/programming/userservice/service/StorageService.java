package com.programming.userservice.service;

import com.main.progamming.common.error.exception.DataNotFoundException;
import com.programming.userservice.domain.persistent.entity.User;
import com.programming.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

@Service
@RequiredArgsConstructor
public class StorageService {
    private final UserRepository userRepository;
    private String FOLDER_PATH = Paths.get("./user-data/photos/").toAbsolutePath().toString();
    private String[] validExtensions = {".jpg", ".jpeg", ".png", ".gif"};

    public boolean isFileImage(MultipartFile file) {
        if(file == null || file.isEmpty()) {
            return false;
        }
        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf(".")).toLowerCase();
        return Arrays.asList(validExtensions).contains(extension);
    }

    public String uploadImageToFileSystem(MultipartFile file) {
        String filePath=FOLDER_PATH + "/" +file.getOriginalFilename();
        System.out.println(filePath);
        try {
            if(!isFileImage(file)) {
                return "";
            }
            // auto create directory if it doesn't exist
            File folder = new File(FOLDER_PATH);
            if (!folder.exists()) {
                FileUtils.forceMkdir(folder);
            }

            file.transferTo(new File(filePath));
            return filePath;
        } catch (IOException e) {
            return "";
        }
    }

    public byte[] loadImageFromFileSystem(String usename) {
        User user = userRepository.findByUserName(usename);
        if(user == null) {
            return "Not Found".getBytes();
        }
        String filePath= user.getPhotos();
        try {
            return Files.readAllBytes(new File(filePath).toPath());
        } catch (IOException e) {
            return "Not Found".getBytes();
        }
    }
}
