package org.example.webboot.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private static final String UPLOAD_DIR = "D:/JAVA SHIT/web02/public/images/";  // 外部路径

    public String saveAvatar(MultipartFile avatar) {
        String fileName = avatar.getOriginalFilename();
        if (fileName != null) {
            Path path = Paths.get(UPLOAD_DIR + fileName);  // 保存到 D:/JAVA SHIT/web02/public/images/ 目录下
            try {
                Files.createDirectories(path.getParent());  // 确保目录存在
                Files.copy(avatar.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                return "/images/" + fileName;  // 返回相对路径，用于前端展示
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}


