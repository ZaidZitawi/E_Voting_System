package com.example.e_voting_system.Services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
public class FileUploadService {

    // Inject the upload directory from application.properties
    @Value("${file.upload-dir}")
    private String uploadDir;

    public String uploadFile(MultipartFile file, String subFolder) throws IOException {
        // Validate file
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File is empty or null.");
        }

        // Generate a unique filename
        String originalFileName = file.getOriginalFilename();
        String fileExtension = "";

        if (originalFileName != null && originalFileName.contains(".")) {
            fileExtension = originalFileName.substring(originalFileName.lastIndexOf('.'));
        }
        String uniqueFileName = UUID.randomUUID().toString() + fileExtension;

        // Build the full directory path
        Path uploadPath = Paths.get(uploadDir, subFolder).toAbsolutePath().normalize();
        Files.createDirectories(uploadPath); // Create the directory if it doesn't exist

        // Build the full file path
        Path filePath = uploadPath.resolve(uniqueFileName);

        // Save the file to the directory
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return the relative path for database storage
        // Adjust this based on how you serve static files
        return subFolder + "/" + uniqueFileName;
    }
}
