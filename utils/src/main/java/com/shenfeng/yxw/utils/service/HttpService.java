package com.shenfeng.yxw.utils.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.util.MultiValueMap;

import java.nio.file.Paths;
import java.util.Arrays;

/**
 * @Author yangxw
 * @Date 2020-09-21 下午4:47
 * @Description
 * @Version 1.0
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class HttpService {
    private final RestTemplate restTemplate;


    public void formSubmit() {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA); // 多部件表单体

        MultipartBodyBuilder multipartBodyBuilder = new MultipartBodyBuilder();
        // ----------------- 表单 part
        multipartBodyBuilder.part("name", "KevinBlandy");
        /**
         * 每个表单项都有独立的header
         * 在这个表单项后额外添加了一个header
         */
        multipartBodyBuilder.part("skill", Arrays.asList("Java", "Python", "Javascript")).header("myHeader", "myHeaderVal");

        // ----------------- 文件 part
        // 从磁盘读取文件
        multipartBodyBuilder.part("file", new FileSystemResource(Paths.get("D:\\17979625.jpg")), MediaType.IMAGE_JPEG);
        // 从classpath读取文件
        multipartBodyBuilder.part("file", new ClassPathResource("app.log"), MediaType.TEXT_PLAIN);
//        multipartBodyBuilder.part("file",request.getFile("file").getResource());
        // ----------------- json part
        // json表单项
        multipartBodyBuilder.part("json", "{\"website\": \"SpringBoot中文社区\"}", MediaType.APPLICATION_JSON);

        // build完整的消息体
        MultiValueMap<String, HttpEntity<?>> multipartBody = multipartBodyBuilder.build();
        ResponseEntity<String> responseEntity = restTemplate.postForEntity("http://localhost/test", multipartBody, String.class);

        System.out.println(responseEntity);


    }
}
