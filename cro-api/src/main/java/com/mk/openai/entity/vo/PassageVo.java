package com.mk.openai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PassageVo {
    private Integer passageId;
    private String passage;
    private Integer regulationId;
    private String fileName;
    private Integer categoryId;
    private String categoryName;
    private Double score;

}
