package com.cn.chat.bridge.admin.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.ObjectMetadata;
import com.cn.chat.bridge.admin.dto.AliOssConfigDto;
import com.cn.chat.bridge.admin.service.SystemService;
import com.cn.chat.bridge.common.constant.CodeEnum;
import com.cn.chat.bridge.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class AliUploadServiceImpl {

    private final SystemService systemService;

    public String uploadFile(MultipartFile file, String path, String newFileName, boolean isImage) {
        AliOssConfigDto aliOssConfig = systemService.getAliOssConfig();
        OSS ossClient = new OSSClientBuilder()
                .build(aliOssConfig.getEndpoint(), aliOssConfig.getAccessKey(), aliOssConfig.fetchSecretKey());
        try (InputStream inputStream = file.getInputStream()) {
            String originalFileName = file.getOriginalFilename();

            assert originalFileName != null;
            String fileName;
            fileName = Objects.requireNonNullElseGet(newFileName, () -> UUID.randomUUID() + originalFileName.substring(originalFileName.lastIndexOf('.')));

            String filePath = path + "/" + fileName;

            if (isImage) {
                ObjectMetadata objectMetadata = new ObjectMetadata();
                objectMetadata.setContentType("image/jpg");
                ossClient.putObject(aliOssConfig.getBucketName(), filePath, inputStream, objectMetadata);
            } else {
                ossClient.putObject(aliOssConfig.getBucketName(), filePath, inputStream);
            }

            return "/" + filePath;
        } catch (IOException e) {
            log.error("无法将图片上传到阿里云。错误消息： {} 错误类： {}", e.getMessage(), e.getClass());
            throw BusinessException.create(CodeEnum.FILE_UPLOAD_ERROR);
        } finally {
            ossClient.shutdown();
        }
    }
}
