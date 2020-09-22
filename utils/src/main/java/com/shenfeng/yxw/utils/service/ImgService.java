package com.shenfeng.yxw.utils.service;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.apache.catalina.webresources.FileResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author yangxw
 * @Date 2020-09-21 下午4:20
 * @Description
 * @Version 1.0
 */
@Service
@Slf4j
public class ImgService {

    public BufferedImage cropImg(InputStream inputStream, double pLeftX, double pRightX, double pLeftY, double pRightY) {
//        // 原始图片路径
//        String imgPath = "C:\\Users\\84330\\Desktop\\test\\371E0E19-F8A2-47F2-B6B2-915E417EC6CA.jpg";
//        // 压缩图片路径
//        String smallImgPath = imgPath.replace(".", "_small.");

        try {

            BufferedImage bufferedImage = ImageIO.read(inputStream);
            int width = bufferedImage.getWidth();
            int height = bufferedImage.getHeight();
            int x = (int) (width * pLeftX);
            int y = (int) (height * pLeftY);
            int cropWidth = (int) ((pRightX - pLeftX) * width);
            int cropHeight = (int) ((pRightY - pLeftY) * height);

            log.info("x:【{}】,y:【{}】，width:【{}】,height:【{}】", x, y, cropWidth, cropHeight);
            // 指定坐标
//            Thumbnails.of(bufferedImage)
//                    .sourceRegion(x, y, cropWidth, cropHeight)
//                    .size(cropWidth, cropHeight)
//                    .keepAspectRatio(false)
//                    .toFile(smallImgPath);

            BufferedImage asBufferedImage = Thumbnails.of(bufferedImage)
                    .sourceRegion(x, y, cropWidth, cropHeight)
                    .size(cropWidth, cropHeight)
                    .keepAspectRatio(false)
                    .asBufferedImage();
            return asBufferedImage;


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cropImg() {
        // 原始图片路径
        String imgPath = "C:\\Users\\84330\\Desktop\\test\\371E0E19-F8A2-47F2-B6B2-915E417EC6CA.jpg";
        // 压缩图片路径
        String smallImgPath = imgPath.replace(".", "_small.");

        try {

            // 指定坐标
            Thumbnails.of(imgPath)

                    .sourceRegion(10, 600, 800, 200)
                    .size(800, 200)
                    .keepAspectRatio(false)
                    .toFile(smallImgPath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        new ImgService().cropImg();
    }
}
