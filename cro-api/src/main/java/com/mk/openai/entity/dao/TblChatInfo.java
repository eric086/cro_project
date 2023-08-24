package com.mk.openai.entity.dao;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * @TableName tbl_chat_info
 */
@TableName(value ="tbl_chat_info")
@Data
public class TblChatInfo implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 问答ID
     */
    private Integer chatId;

    /**
     * 问题
     */
    private String ask;

    /**
     * 回答
     */
    private String answer;

    /**
     * 提问者
     */
    private Integer questioner;

    /**
     * 提问时间
     */
    private Date createAt;
}