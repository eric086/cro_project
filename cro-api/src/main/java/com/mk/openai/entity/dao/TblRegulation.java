package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 法规表
 * @TableName tbl_regulation
 */
@TableName(value ="tbl_regulation")
@Data
public class TblRegulation implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 文件名
     */
    private String fileName;

    /**
     * 文件相对路径
     */
    private String filePath;

    /**
     * 法规类别id
     */
    private Integer categoryId;

}