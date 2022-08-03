package com.blog;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.fill.Column;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * @program: blog-admin
 * @description: 执行 main 方法控制台输入模块表名回车自动生成对应项目目录中
 * @author: Zx
 * @create: 2022-08-02 14:20
 **/
public class CodeGenerator {

    public static void main(String[] args) {
        String url= "jdbc:mysql://127.0.0.1:3306/blog-admin?useUnicode=true&serverTimezone=GMT&allowPublicKeyRetrieval=true&characterEncoding=utf8";
        String name = "root";
        String password = "123456";

        FastAutoGenerator.create(url,name,password)
                // 全局配置
                .globalConfig((scanner,builder) ->
                        builder.author(scanner.apply("请输入作者名称？"))
                                // 输出目录
                                .outputDir(System.getProperty("user.dir")+"\\src\\main\\java")
                                // 覆盖已生成文件
                                .fileOverride()
                                // 禁止打开输出目录：默认 true
                                .disableOpenDir()

                )
                // 包配置
                .packageConfig((scanner, builder) ->
                        builder.parent(scanner.apply("请输入包名？"))
                                // 自定义实体包名
                                .entity("pojo")
                                // 设置mapperXml生成路径
                                .pathInfo(Collections.singletonMap(OutputFile.mapperXml, System.getProperty("user.dir")+"\\src\\main\\resources\\mapper"))

                )
                // 策略配置
                .strategyConfig((scanner, builder) ->
                        builder.addInclude(getTables(scanner.apply("请输入表名，多个英文逗号分隔？所有输入 all")))
                                .addTablePrefix("m_", "t_") // 设置过滤表前缀
                                // 控制层配置
                                .controllerBuilder().enableRestStyle().enableHyphenStyle()
                                // 服务层配置
                                .serviceBuilder()
                                // 去除服务接口前的I前缀，%s表示实体类名：[实体类名 + Service]
                                .formatServiceFileName("%sService")
                                // 实体层配置
                                .entityBuilder()
                                .enableLombok()
                                // 主键策略
                                .idType(IdType.ASSIGN_ID)
                                // 时间自动填充
                                //.addTableFills(
                                //        new Column("gmt_create", FieldFill.INSERT),
                                //        new Column("gmt_modified", FieldFill.INSERT_UPDATE)
                                //)
                                .addTableFills(new Column("gmt_create", FieldFill.INSERT))
                                .addTableFills(new Column("gmt_modified", FieldFill.INSERT_UPDATE))
                                // 乐观锁
                                .versionColumnName("version")
                                .versionPropertyName("version")
                                // 逻辑删除
                                .logicDeleteColumnName("deleted")
                                .logicDeletePropertyName("deleted")
                                .build()

                )
                /*
                    模板引擎配置，默认 Velocity 可选模板引擎 Beetl 或 Freemarker
                   .templateEngine(new BeetlTemplateEngine())
                   .templateEngine(new FreemarkerTemplateEngine())
                 */
                .execute();
    }

    // 处理 all 情况
    protected static List<String> getTables(String tables) {
        return "all".equals(tables) ? Collections.emptyList() : Arrays.asList(tables.split(","));
    }

}

