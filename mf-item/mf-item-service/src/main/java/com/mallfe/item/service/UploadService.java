package com.mallfe.item.service;

import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * 描述
 *
 * @author yangbin
 * @since 2019/9/14
 */
@Service
public class UploadService {
    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg", "image/png");

    public String uoloadImage(MultipartFile file) {
        try{
            //校验文件类型
            String contentType = file.getContentType();
            if(!ALLOW_TYPES.contains(contentType)){
                throw new MallfeException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            //校验文件内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new MallfeException(ExceptionEnum.INVALID_FILE_TYPE);
            }

            File dest = new File("/Users/yangbin/Documents/Mallfe/upload/", file.getOriginalFilename());

            file.transferTo(dest);

            return "http://127.0.0.1/image/" + file.getName();
        }
        catch (IOException e){
            throw new MallfeException(ExceptionEnum.INVALID_FILE_TYPE);
        }

    }
}
