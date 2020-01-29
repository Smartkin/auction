package com.badcompany.auction.services;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    @Value("${app.upload.dir:${user.home}}")
    public String uploadDir;

    public void uploadFile(MultipartFile file) throws IOException {
        uploadFile(file, "");
    }

    public void uploadFile(MultipartFile file, String path) throws IOException {
        Path location = Paths.get(uploadDir + File.separator + path);
        if (!Files.exists(location)) {
            Files.createDirectories(location);
        }
        Path copyLocation = Paths
                .get(location + File.separator + StringUtils.cleanPath(file.getOriginalFilename()));
        File targetFile = new File(copyLocation.toString());
        if (!targetFile.exists()) {
            targetFile.createNewFile();
            FileOutputStream bos = new FileOutputStream(targetFile);
            bos.write(file.getBytes());
        }
    }

    public Path getFile(String fileName) {
        return Paths.get(uploadDir).resolve(fileName);
    }

    public Resource load(String fileName) throws MalformedURLException {
        Path file = getFile(fileName);
        return new UrlResource(file.toUri());
    }
}
