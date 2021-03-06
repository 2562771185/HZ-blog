package com.jhzz.jhzzblog;

import com.jhzz.jhzzblog.entity.SysUser;
import com.jhzz.jhzzblog.service.IArticleService;
import com.jhzz.jhzzblog.service.SysUserService;
import com.jhzz.jhzzblog.utils.JWTUtils;
import com.jhzz.jhzzblog.utils.QiniuUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@SpringBootTest
class JhzzBlogApplicationTests {

    @Test
    void contextLoads() {
//        FastAutoGenerator.create("jdbc:mysql://localhost:3306/blog", "root", "123456mjz...")
//                .globalConfig(builder -> {
//                    builder.author("Jzhz") // 设置作者
//                            //.enableSwagger() // 开启 swagger 模式
//                            .fileOverride() // 覆盖已生成文件
//                            .outputDir("D:\\Code\\jhzzBlog\\src\\main\\java\\com\\jhzz\\jhzzblog"); // 指定输出目录
//                })
//                .packageConfig(builder -> {
//                    builder.parent("generator") // 设置父包名
//                            .moduleName("jhzzblog") // 设置父包模块名
//                            .pathInfo(Collections.singletonMap(OutputFile.mapper, "D:\\Code\\jhzzBlog\\src\\main\\resources\\mapper")); // 设置mapperXml生成路径
//                })
//                .strategyConfig(builder -> {
//                    builder.addInclude("ms_category") // 设置需要生成的表名
//                            .addTablePrefix("ms_", "t_"); // 设置过滤表前缀
//                })
//                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
//                .execute();
    }
    @Autowired
    IArticleService service;
    @Test
    void selectTest() {
        String token = JWTUtils.createToken(100L);
        System.out.println("token = " + token);
        Map<String, Object> map = JWTUtils.checkToken(token);
        System.out.println(map.get("userId"));
    }
    @Autowired
    SysUserService sysUserService;
    @Test
    void selectTest1() {
        SysUser admin = sysUserService.findUserByAccount("admin");
        System.out.println("admin = " + admin);
    }
    @Autowired
    private QiniuUtils qiniuUtils;
    @Test
    void qiniuDeleteTest() {
//        qiniuUtils.deleteFile("d35b30f4-9a1d-4d91-a0af-a0ec95aba178.jpg");
        Long time = 1651675367856L;
        Date date = new Date(time);
        DateFormat dateFormat = new SimpleDateFormat("M");
        String format = dateFormat.format(date);

//        2022-05-04
        System.out.println(format);

    }
}
