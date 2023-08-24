package com.mk.openai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectVo {
    private Integer id;
    private String name;
    private Integer createBy;
    private String createByName;
    private String createAt;
    private String updateAt;
    private ProjectBaseInfoVo baseInfo;
    private ProjectExtendInfoVo extendInfo;
}
