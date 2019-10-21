package com.mallfe.item.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.m.fastdfs.FastDFSFile;
import com.m.fastdfs.FileManager;
import com.mallfe.common.enums.ExceptionEnum;
import com.mallfe.common.exception.MallfeException;
import com.mallfe.common.json.JsonData;
import com.mallfe.common.json.JsonObject;
import com.mallfe.common.utils.FileUploadingUtil;
import com.mallfe.item.pojo.UploadRequestMessage;
import com.mallfe.item.pojo.UploadResponseMessage;
import net.sf.json.JSONArray;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;

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

    private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg","image/jpg", "image/png");

    public String uploadImage(MultipartFile file) {
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
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(),extension,null);
            return "http://120.201.127.207:2389/"+storePath.getFullPath();
        }
        catch (IOException e){
            throw new MallfeException(ExceptionEnum.INVALID_FILE_TYPE);
        }

    }

    @Transactional(rollbackFor = Exception.class)
    public JsonObject fileUpload(UploadRequestMessage request, HttpServletRequest servlet) {
        JsonData jsonData = new JsonData();
        UploadResponseMessage response = new UploadResponseMessage();
        try {
            String filePath = "/opt";
            filePath += request.getUserid() + "/";

            MultipartHttpServletRequest mRequest = (MultipartHttpServletRequest) servlet;
            Map<String, String> uploadedFiles = FileUploadingUtil.upload(filePath, mRequest.getFileMap());
            Map<String, String> returnMessage = new HashMap<String, String>();
            Iterator<Entry<String, String>> iter = uploadedFiles.entrySet().iterator();
            FileInputStream fis = null;
            while (iter.hasNext()) {
                Entry<String, String> each = iter.next();
                String[] codeStr = each.getKey().split("\\.");
                File content = new File(each.getValue());
                fis = new FileInputStream(content);
                byte[] file_buff = null;
                if (fis != null) {
                    int len = fis.available();
                    file_buff = new byte[len];
                    fis.read(file_buff);
                }
                FastDFSFile file = new FastDFSFile(codeStr[0], file_buff, codeStr[1]);
                String retUrl = FileManager.getOnly().upload(file);
                if (retUrl != null && retUrl != "") {
                    retUrl = retUrl.replace(":80", ":8888");
                }
                returnMessage.put(each.getKey(), retUrl);
            }
            if (fis != null)
                fis.close();
            response.setReturnCode("00");
            response.setReturnMessage(JSONArray.fromObject(returnMessage).toString());

        } catch (Exception e) {
            response.setReturnCode("01");
            response.setReturnMessage("保存失敗");
            e.printStackTrace();
        }
        jsonData = new JsonData(response);
        return jsonData;
    }

    public JsonObject uploadImageReturnJson(MultipartFile file) {
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
            StorePath storePath = fastFileStorageClient.uploadImageAndCrtThumbImage(file.getInputStream(),file.getSize(),extension,null);
            return new JsonData(storePath.getFullPath());
        }
        catch (IOException e){
            throw new MallfeException(ExceptionEnum.INVALID_FILE_TYPE);
        }
    }
}
