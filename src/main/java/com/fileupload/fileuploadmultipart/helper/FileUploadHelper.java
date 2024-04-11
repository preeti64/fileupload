package com.fileupload.fileuploadmultipart.helper;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUploadHelper {

    public final String UPLOAD_DIR = new ClassPathResource("static/images/").getFile().getAbsolutePath();

    public FileUploadHelper() throws IOException {
    }
    // public final String UPLOAD_DIR = "C:\\Users\\User\\Desktop\\SpringBoot\\fileuploadmultipart\\src\\main\\resources\\static\\images";

    public boolean uploadFile(MultipartFile multipartFile){
        boolean isUploaded = false;//by default file is not uploaded

        try{
            //Files.copy(inputStream, target, options)
            //InputStream -> to read the content of file
            //target -> location where file is to be uploaded
            //options -> to configure how the file has to be copied
            Files.copy(multipartFile.getInputStream(),
                    Paths.get(UPLOAD_DIR + File.separator + multipartFile.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);

            isUploaded = true;

        }catch(Exception e){
            e.printStackTrace();
        }

        return isUploaded;
    }
}
