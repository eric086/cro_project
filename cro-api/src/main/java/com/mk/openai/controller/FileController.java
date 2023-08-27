package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.common.user.RequestScopeLoginUserHolder;
import com.mk.openai.entity.dao.TblFile;
import com.mk.openai.entity.vo.FileVo;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.service.TblFileService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/api/file")
public class FileController {

    private final RequestScopeLoginUserHolder loginUserHolder;

    private final TblFileService tblFileService;

    @PostMapping("/upload")
    public JsonResponse<FileVo> upload(@RequestParam String type, @RequestParam MultipartFile file) {
        return JsonResponse.<FileVo>builder().data(tblFileService.uploadFile(type,
                loginUserHolder.getCurrentUser().getUserId(), file)).wrapSuccess().build();
    }

    @GetMapping(value = "/show/{fileId}")
    public ResponseEntity<Resource> show(@PathVariable Integer fileId) throws IOException {
        TblFile fileDao =
                tblFileService.getById(fileId);

        if (Objects.isNull(fileDao)) {
            throw new ServiceException("show file error!");
        }

        Path filePath = Paths.get(fileDao.getFilePath()); // 替换为您的文件路径
        Resource resource = new org.springframework.core.io.UrlResource(filePath.toUri());

        String filename = fileDao.getOriginalFilename();

        String suffix = filename.substring(filename.lastIndexOf(".")).toLowerCase();

        // 设置响应的Content-Type为文件的MIME类型
        String contentType = "application/octet-stream";

        switch (suffix) {
            // 如果是JPEG或JPG图片
            case ".jpg":
            case ".jpeg":
                contentType = "image/jpeg";
                break;
            // 如果是PNG图片
            case ".png":
                contentType = "image/png";
                break;
            // 如果是doc文件
            case ".doc":
                contentType = "application/msword";
                break;
            // 如果是docx文件
            case ".docx":
                contentType = "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;
            // 如果是ppt文件
            case ".ppt":
                contentType = "application/vnd.ms-powerpoint";
                break;
            // 如果是pptx文件
            case ".pptx":
                contentType = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                break;
            // 如果是pdf文件
            case ".pdf":
                contentType = "application/pdf";
                break;
        }

        MediaType mediaType = MediaType.parseMediaType(contentType);

        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(resource);

    }


}
