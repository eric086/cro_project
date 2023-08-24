package com.mk.openai.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectRegulationVo {
    private Integer projectId;
    private Integer regulationId;
    private String fileName;
    private String filePath;
    private Integer categoryId;
    private String categoryName;

}
