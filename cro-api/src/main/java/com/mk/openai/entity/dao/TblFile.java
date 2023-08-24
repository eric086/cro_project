package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;


@TableName(value ="tbl_file")
@Data
public class TblFile implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer uploader;

    /**
     * 
     */
    private String filePath;

    /**
     * 
     */
    private String originalFilename;

    /**
     * 创建时间
     */
    private Date createAt;
}