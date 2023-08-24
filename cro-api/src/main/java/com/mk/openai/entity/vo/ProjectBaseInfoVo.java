package com.mk.openai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBaseInfoVo {
    private Integer total;
    private  Integer askNumber;
    private Integer noAskNumber;
    private List<BaseAskVo> list;
}
