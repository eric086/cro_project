package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;


@TableName(value ="tbl_project_base")
@Data
@Builder
public class TblProjectBase implements Serializable {
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
     * 问题
     */
    private String ask;

    /**
     * 回答
     */
    private String answer;

    /**
     * 排序
     */
    private Integer sort;


}