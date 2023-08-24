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
public class ExtendInfoVo {
    private String keyword;
    private String text;
    private List<FileVo> files;
    private List<FileVo> imgs;
}
