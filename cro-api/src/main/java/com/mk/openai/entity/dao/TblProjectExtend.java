package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@TableName(value ="tbl_project_extend")
@Data
@Builder
public class TblProjectExtend implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 项目ID
     */
    private Integer projectId;

    /**
     * 关键字
     */
    private String keyword;

    /**
     * 文本内容
     */
    private String text;

    /**
     * 文件内容(上传文件的ID以,分割)
     */
    private String file;

    /**
     * 图片内容(上传图片的ID以,分割)
     */
    private String img;

}