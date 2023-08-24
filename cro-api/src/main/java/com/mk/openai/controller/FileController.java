package com.mk.openai.controller;

import com.mk.openai.common.JsonResponse;
import com.mk.openai.entity.common.user.RequestScopeLoginUserHolder;
import com.mk.openai.entity.dao.TblFile;
import com.mk.openai.entity.vo.FileVo;
import com.mk.openai.exception.ServiceException;
import com.mk.openai.service.TblFileService;
import com.mk.openai.service.TblProjectUserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
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

    @GetMapping(value = "/download/{fileId}")
    public void download(@PathVariable Integer fileId, HttpServletResponse response) {
        try {
            TblFile fileDao =
                    tblFileService.getById(fileId);

            if (Objects.isNull(fileDao)) {
                throw new ServiceException("download file error!");
            }

            File file = new File(fileDao.getFilePath());
            String filename = fileDao.getOriginalFilename();

            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            response.reset();
            response.setCharacterEncoding("UTF-8");
            response.addHeader(
                    "Content-Disposition",
                    "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));

            response.addHeader("Content-Length", "" + file.length());
            response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
            throw new ServiceException("download file error!");
        }
    }


}
