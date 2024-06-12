package com.example.springbootdemo.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.nio.file.Paths;
import java.util.Collections;

public class MyBatisPlusGenerator {
    public static void main(String[] args) {
        // remarks=true&useInformationSchema=true 解决无法读取表注释问题
        // &tinyInt1isBit=true 解决tinyint字段转换问题
        String url = "jdbc:mysql://127.0.0.1:3306/springbootdemo?tinyInt1isBit=true&remarks=true&useInformationSchema=true";
        String username = "root";
        String password = "123456";
        String classOutputDir = Paths.get(System.getProperty("user.dir")) + "/src/main/java";
        String resourceOutputDir = Paths.get(System.getProperty("user.dir")) + "/src/main/resources";
        FastAutoGenerator.create(url, username, password)
                .globalConfig(builder -> builder
                        .author("heeron")
                        .outputDir(classOutputDir)
                        .commentDate("yyyy-MM-dd HH:mm:ss")
                )
                .packageConfig(builder -> builder
                        .parent("com.example.springbootdemo")
                        .entity("domain.entity")
                        .mapper("mapper")
                        .service("service")
                        .serviceImpl("service.impl")
                        .xml("mapper")
                        .pathInfo(Collections.singletonMap(OutputFile.xml, resourceOutputDir + "/mapper"))
                )
                .strategyConfig(builder -> builder
                        .addInclude("user") // 设置需要生成的表名 addTablePrefix设置过滤表前缀
                        .entityBuilder()
                        .enableLombok() // 启用 Lombok
                        .enableTableFieldAnnotation()  // 启用字段注解
                        .controllerBuilder()
                        .enableRestStyle() // 启用 REST 风格
                )
                .templateEngine(new FreemarkerTemplateEngine())
                .execute();
    }
}
