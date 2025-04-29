package org.example.webboot.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileService {
    private static final String UPLOAD_DIR = "D:/JAVA SHIT/web02/public/images/";  // 外部路径
    private static final String ACCESS_PATH = "/images/";  // 前端访问路径

    public String saveAvatar(MultipartFile avatar) {
        String fileName = avatar.getOriginalFilename();
        if (fileName != null) {
            Path path = Paths.get(UPLOAD_DIR + fileName);  // 保存到 D:/JAVA SHIT/web02/public/images/ 目录下
            try {
                Files.createDirectories(path.getParent());
                Files.copy(avatar.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
                return "/images/" + fileName;
            } catch (IOException e) {
                e.printStackTrace(); // 这里可能抛出异常！
            }
        }
        return null;
    }
}


