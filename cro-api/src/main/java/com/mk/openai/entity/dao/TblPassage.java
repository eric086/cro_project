package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;


@TableName(value ="tbl_passage")
@Data
public class TblPassage implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 法规id
     */
    private Integer regulationId;

    /**
     * 段落内容
     */
    private String content;

}