package org.example.webboot.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class FileUtil {

    @Value("${app.upload.dir:uploads}")
    private static String baseUploadDir; // 从配置读取，默认uploads目录

    /**
     * 上传文件到指定子目录
     * @param file 上传的文件
     * @param subDir 子目录 (如 "/avatar")
     * @return 文件存储路径 (相对路径)
     */
    public static String upload(MultipartFile file, String subDir) throws IOException {
        if (file == null || file.isEmpty()) {
            return null;
        }

        String uploadDir = baseUploadDir + subDir;
        Path uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String originalFilename = file.getOriginalFilename();
        String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        String filename = UUID.randomUUID() + extension;

        Path targetPath = uploadPath.resolve(filename);
        file.transferTo(targetPath);

        return subDir + "/" + filename; // 返回相对路径
    }

}