package com.taian.controller;

import com.taian.entity.Image;
import com.taian.service.IImageService;
import com.taian.util.DateUtils;
import com.taian.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Controller
@RequestMapping("/image")
public class ImageController {

    @Autowired
    IImageService iImageService;


    @PostMapping("/add")
    public Result addImage(
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @RequestParam("articleId") Integer articleId

    ){
        //改文件名字
        String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();
        String originalFilename = multipartFile.getOriginalFilename();
        int i = originalFilename.lastIndexOf(".");
        String newFileName = uuid.concat(originalFilename.substring(i));
        System.out.println(newFileName);

        //保存文件，将文件发送到第三方服务
        String portraitPath = "D:/Works/work/GraduationProject/src/main/java/com/taian/upload/articleImage/".concat(newFileName);

        //发送文件
        try {
            multipartFile.transferTo(new File(portraitPath));
            System.out.println("发送了文件");
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image image = new Image();
        image.setArticleId(articleId);
        image.setImageKey(uuid);
        image.setUploadTime(DateUtils.getDateNowTimeNo());
        System.out.println(image);

        iImageService.save(image);

        System.out.println(image);
        System.out.println("存了图片");

        return Result.ok();
    }

}
