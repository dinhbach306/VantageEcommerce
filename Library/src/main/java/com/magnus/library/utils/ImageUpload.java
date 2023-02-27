package com.magnus.library.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component //Đánh dấu là bean
public class ImageUpload {
    private final String UPLOAD_FOLDER = "C:\\Users\\ADMIN\\Magnusjs\\Desktop\\Practice\\Project\\ecommerce-springboot\\Admin\\src\\main\\resources\\static\\img\\image-product";

    public boolean isUploadImage(MultipartFile imageProduct){
        boolean is_Upload = false;
        try {
//            Xử lý lưu ảnh
            Files.copy(imageProduct.getInputStream(),
                    Paths.get(UPLOAD_FOLDER + File.separator, imageProduct.getOriginalFilename()),
                    StandardCopyOption.REPLACE_EXISTING);

            is_Upload = true;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return is_Upload;
    }

    public boolean checkExisted(MultipartFile imageProduct) {
        boolean isExisted = false;
         try {
             File file = new File(UPLOAD_FOLDER + "\\" + imageProduct.getOriginalFilename());
             isExisted = file.exists();
         } catch (Exception e) {
             e.printStackTrace();
         }
         return isExisted;
    }
}
