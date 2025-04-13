package org.example.webboot.util;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileService {

    private static final String UPLOAD_DIR = "D:/JAVA SHIT/Webboot/webboot/src/main/resources/static/uploads/";  // 上传文件保存的目录

    public String saveAvatar(MultipartFile avatar) {
        // 获取文件名
        String fileName = avatar.getOriginalFilename();
        if (fileName != null) {
            // 创建文件路径
            Path path = Paths.get(UPLOAD_DIR + fileName);
            try {
                // 保存文件
                Files.copy(avatar.getInputStream(), path);
                return path.toString(); // 返回文件的路径
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
