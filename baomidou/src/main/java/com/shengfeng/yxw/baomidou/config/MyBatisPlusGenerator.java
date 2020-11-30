package com.shengfeng.yxw.baomidou.config;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.FileOutConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.TemplateConfig;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

/**
 * @Author yangxw
 * @Date 2020-11-27 下午3:43
 * @Description
 * @Version 1.0
 */
public class MyBatisPlusGenerator {

    // 是否覆盖
    private static final boolean fileOverride = true;
    // 需要覆盖的表名
    private static final String[] tableName = {"user", "user_info"};
    // 如果不是子模块的话直接写/src/main，如果是子模块的是 就是module_name+/src/main
    private static final String outPutDir = "/baomidou/src/main";
    // 作者
    private static final String author = "Yang xw";
    // mysql jdbc
    private static final String url = "jdbc:mysql://127.0.0.1/baomidou?serverTimezone=UTC";
    private static final String username = "root";
    private static final String password = "123456";
    private static final String driverClass = "com.mysql.cj.jdbc.Driver";
    // 父目录
    private static final String parent = "com.shengfeng.yxw.baomidou";


    /**
     * <p>
     * 读取控制台内容
     * </p>
     */
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator autoGenerator = new AutoGenerator();


        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setFileOverride(fileOverride);
        String projectPath = System.getProperty("user.dir");
        globalConfig.setOutputDir(projectPath + outPutDir + "/java");
        globalConfig.setAuthor(author);
        globalConfig.setOpen(false);
        autoGenerator.setGlobalConfig(globalConfig);

        // 数据源配置 需配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(url);
        // dataSourceConfig.setSchemaName("public");
        dataSourceConfig.setDriverName(driverClass);
        dataSourceConfig.setUsername(username);
        dataSourceConfig.setPassword(password);
        autoGenerator.setDataSource(dataSourceConfig);

        // 生成包配置
        PackageConfig packageConfig = new PackageConfig();

        // 如果需要手动输入模块名
        // packageConfig.setModuleName(scanner("模块名"));
//        packageConfig.setModuleName("user");
        packageConfig.setParent(parent);
        autoGenerator.setPackageInfo(packageConfig);

        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();

        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {

                // 自定义输出文件名
                return projectPath + outPutDir + "/resources/mapper/" + packageConfig.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        injectionConfig.setFileOutConfigList(focList);
        autoGenerator.setCfg(injectionConfig);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();

        // 配置自定义输出模板
        // 指定自定义模板路径，注意不要带上.ftl/.vm, 会根据使用的模板引擎自动识别
        // templateConfig.setEntity("templates/entity-test.java");
        // templateConfig.setService();
        // templateConfig.setController();

        templateConfig.setXml(null);
        autoGenerator.setTemplate(templateConfig);

        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        // strategyConfig.setSuperEntityClass("com.baomidou.ant.common.BaseEntity");
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setRestControllerStyle(true);
        // strategyConfig.setSuperControllerClass("com.baomidou.ant.common.BaseController");

        // 如果 setInclude() 不加参数, 会自定查找所有表
        // 如需要制定单个表, 需填写参数如: strategyConfig.setInclude("user_info);
        strategyConfig.setInclude(tableName);
        // strategyConfig.setSuperEntityColumns("id");
        strategyConfig.setControllerMappingHyphenStyle(true);

        // 自动将数据库中表名为 user_info 格式的专为 UserInfo 命名
        strategyConfig.setTablePrefix(packageConfig.getModuleName() + "_");
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        System.out.println("===================== MyBatis Plus Generator ==================");

        autoGenerator.execute();

        System.out.println("================= MyBatis Plus Generator Execute Complete ==================");
    }
}
