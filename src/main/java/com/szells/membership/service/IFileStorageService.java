package com.szells.membership.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;

/**
 * @author Riya Patel
 */
public interface IFileStorageService {
    void init();

    String store(MultipartFile file);

}
