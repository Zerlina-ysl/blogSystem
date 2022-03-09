package com.company.vueblog.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

/**
 * Created by IntelliJ IDEA.
 * User: luna
 * Date: 2022/3/9
 */
public interface IOssService {


    public String upload(MultipartFile file);

}
