package com.taian.config;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

/**
 * MyBatisPuls的代码生成器
 */
public class MyFastGeneratorConfiguration {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://localhost:3306/graduation_project?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=true","root", "123456")
                .globalConfig(builder -> {
                    builder.author("taian") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://new"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("com.taian") // 设置父包名
                            .moduleName("test") // 设置父包模块名
                            .pathInfo(Collections.singletonMap(OutputFile.xml,"D://taian-crm")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude("vote") // 设置需要生成的表名
//.addTablePrefix("act_fo_")
                    ; // 设置过滤表前缀
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
