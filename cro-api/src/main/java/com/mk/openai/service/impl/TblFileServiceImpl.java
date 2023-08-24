package com.mk.openai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mk.openai.config.ConfigMap;
import com.mk.openai.entity.dao.TblFile;
import com.mk.openai.entity.vo.FileVo;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.service.TblFileService;
import com.mk.openai.mapper.TblFileMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
@AllArgsConstructor
public class TblFileServiceImpl extends ServiceImpl<TblFileMapper, TblFile>
    implements TblFileService{

    private final ConfigMap configMap;

    private final static String DOC = ".doc";
    private final static String DOCX = ".docx";
    private final static String PDF = ".pdf";
    private final static String PPT = ".ppt";
    private final static String PPTX = ".pptx";

    private final static String JPG = ".jpg";
    private final static String JPEG = ".jpeg";
    private final static String PNG = ".png";


    @Override
    public FileVo uploadFile(String type, Integer userId, MultipartFile file) {

        if(!checkFileType(type,file.getOriginalFilename())){
            throw new ServiceException("上传文件类型错误");
        }

        try {
            String dir = new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "/";
            String path = configMap.getUploadFilePath() + File.separator + dir;
            File exist = new File(path);

            // mkdir
            if (!exist.exists()) {
                exist.mkdirs();
            }
            String id = UUID.randomUUID().toString().replace("-", "");
            String suffix =
                    file.getOriginalFilename()
                            .substring(file.getOriginalFilename().lastIndexOf("."));

            String filePath = path + id + suffix;

            file.transferTo(new File(filePath));

            TblFile tblFile = new TblFile();
            tblFile.setFilePath(filePath);
            tblFile.setOriginalFilename(file.getOriginalFilename());
            tblFile.setUploader(userId);
            save(tblFile);


            return FileVo.builder().fileId(tblFile.getId()).originalFilename(tblFile.getOriginalFilename()).build();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw new ServiceException("上传文件失败");
        }
    }

    private boolean checkFileType(String type, String fileName) {
        switch (type) {
            case "file":
                return fileName.endsWith(DOC) || fileName.endsWith(DOCX) || fileName.endsWith(PPT) || fileName.endsWith(PPTX) || fileName.endsWith(PDF);
            case "image":
                return fileName.endsWith(JPG) || fileName.endsWith(JPEG) || fileName.endsWith(PNG);
            default:
                throw new ServiceException("文件类型不符合");
        }
    }


}




