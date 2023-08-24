package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * 
 * @TableName tbl_project_regulation
 */
@TableName(value ="tbl_project_regulation")
@Data
@Builder
public class TblProjectRegulation implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 
     */
    private Integer projectId;

    /**
     * 
     */
    private Integer regulationId;

}