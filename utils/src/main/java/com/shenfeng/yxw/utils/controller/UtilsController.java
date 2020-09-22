package com.shenfeng.yxw.utils.controller;

import com.shenfeng.yxw.utils.domain.dto.TestStripDto;
import com.shenfeng.yxw.utils.service.ImgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.function.ServerRequest;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.ServerException;
import java.util.Enumeration;

/**
 * @Author yangxw
 * @Date 2020-09-21 下午3:38
 * @Description
 * @Version 1.0
 */
@RestController
@RequestMapping(value = "/utils")
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UtilsController {
    private final ImgService imgService;

    @PostMapping(value = "file-save")
    public Object crop(@RequestParam(value = "file") MultipartFile file, @RequestParam(value = "keyname") String keyname, HttpServletRequest request) throws IOException {

        Enumeration<String> enums = request.getHeaderNames();
        while (enums.hasMoreElements()) {
            String name = enums.nextElement();
            log.info(name + ": {}", request.getHeader(name));
        }

        log.info("file size:【{}】,keyname:【{}】", file.getSize(), keyname);
        String smallImgPath = "C:\\Users\\84330\\Desktop\\test\\llllllllll_" + keyname;
        Thumbnails.of(file.getInputStream())
                .scale(1f)
                .toFile(smallImgPath);

        return "ok";
    }

    @PostMapping(value = "file")
    public Object crop(@RequestParam(value = "file") MultipartFile file, TestStripDto testStripDto) throws IOException {

        BufferedImage asBufferedImage = imgService.cropImg(file.getInputStream(), testStripDto.getPLeftX(), testStripDto.getPRightX(), testStripDto.getPLeftY(), testStripDto.getPRightY());
        if (asBufferedImage == null) {
            throw new ServerException("服务器错误");
        }

        ByteArrayOutputStream os = new ByteArrayOutputStream();
        ImageIO.write(asBufferedImage, "png", os);
        InputStream input = new ByteArrayInputStream(os.toByteArray());
        // as restTemplate param
        InputStreamResource inputStreamResource = new InputStreamResource(input);

        return "ok";
    }

    private static InputStream BaseToInputStream(String base64string) {
        ByteArrayInputStream stream = null;
        try {
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] bytes1 = decoder.decodeBuffer(base64string);
            stream = new ByteArrayInputStream(bytes1);
        } catch (Exception e) {
            // TODO: handle exception
        }
        return stream;
    }
}
