package com.mallfe.item.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
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

    @Autowired
    private FastFileStorageClient fastFileStorageClient;

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
            String extension = StringUtils.substringAfterLast(file.getOriginalFilename(),".");
            StorePath storePath = fastFileStorageClient.uploadFile(file.getInputStream(),file.getSize(),extension,null);
            return "http://120.201.127.207:2389/"+storePath.getFullPath();
        }
        catch (IOException e){
            throw new MallfeException(ExceptionEnum.INVALID_FILE_TYPE);
        }

    }
}
