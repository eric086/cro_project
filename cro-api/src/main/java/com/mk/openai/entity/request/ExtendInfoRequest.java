package com.mk.openai.entity.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtendInfoRequest {

    @NotBlank(message = "关键字不能为空")
    private String keyword;
    private String text;

    @Size(min = 1,message = "至少上传一个文件")
    private List<String> files;

    @Size(min = 1,message = "至少上传一张图片")
    private List<String> imgs;
}
