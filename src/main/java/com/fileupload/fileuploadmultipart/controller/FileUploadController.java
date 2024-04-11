package com.fileupload.fileuploadmultipart.controller;

import com.fileupload.fileuploadmultipart.helper.FileUploadHelper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Objects;

@RestController
public class FileUploadController {

    private final FileUploadHelper fileUploadHelper;

    public FileUploadController(FileUploadHelper fileUploadHelper) {
        this.fileUploadHelper = fileUploadHelper;
    }

    @PostMapping("/upload-file")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("Please select a file");
            }
            if (!Objects.equals(file.getContentType(), "image/jpeg")) {
                return ResponseEntity.badRequest().body("Only jpeg and jpg are allowed");
            }
//Upload file to server
            boolean fileupload = fileUploadHelper.uploadFile(file);
            if (fileupload) {
                return ResponseEntity.ok(ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path("/images/").path(Objects.requireNonNull(file.getOriginalFilename())).toUriString());

                //return ResponseEntity.ok("File uploaded successfully");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.internalServerError().body("Internal Server Error");
    }
}
