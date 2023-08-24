package com.mk.openai.service;

import com.mk.openai.entity.dao.TblFile;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mk.openai.entity.vo.FileVo;
import org.springframework.web.multipart.MultipartFile;


public interface TblFileService extends IService<TblFile> {
    FileVo uploadFile(String type, Integer userId, MultipartFile file);
}
