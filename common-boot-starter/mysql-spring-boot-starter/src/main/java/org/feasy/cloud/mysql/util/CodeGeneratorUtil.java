package org.feasy.cloud.mysql.util;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  代码生成器工具类
 * </p>
 *
 * @author yangxiaohui
 * @since 2020/4/28
 */
@Data
public class CodeGeneratorUtil {
    private String db_url;
    private String db_driver_name;
    private String db_user_name;
    private String db_pass_word;
    private String author;
    private String superServiceClass="org.feasy.cloud.mysql.base.BaseService";
    private String superServiceImplClass="org.feasy.cloud.mysql.base.BaseServiceImpl";

    private CodeGeneratorUtil(String db_url, String db_driver_name, String db_user_name, String db_pass_word, String author) {
        this.db_url = db_url;
        this.db_driver_name = db_driver_name;
        this.db_user_name = db_user_name;
        this.db_pass_word = db_pass_word;
        this.author = author;
    }

    public static CodeGeneratorUtil buildCodeGenerator(String db_url, String db_driver_name, String db_user_name, String db_pass_word, String author){
        return new CodeGeneratorUtil(db_url,db_driver_name,db_user_name,db_pass_word,author);
    }

    public void generatorCode(String projectPath,String page,String ... tables){
        AutoGenerator autoGenerator = new AutoGenerator();
        // 全局配置
        GlobalConfig globalConfig = new GlobalConfig();
        globalConfig.setOutputDir(projectPath + "/src/main/java");
        globalConfig.setAuthor(this.author);
        globalConfig.setSwagger2(false);
        globalConfig.setActiveRecord(false);
        globalConfig.setOpen(false);
        globalConfig.setBaseResultMap(true);
        //globalConfig.setEntityName("");
        globalConfig.setMapperName("%sMapper");
        globalConfig.setServiceName("%sService");
        globalConfig.setServiceImplName("%sServiceImpl");
        globalConfig.setControllerName("%sController");
        // 设置覆盖已有文件
        globalConfig.setFileOverride(false);
        globalConfig.setIdType(IdType.ID_WORKER_STR);

        // 数据源配置
        DataSourceConfig dataSourceConfig = new DataSourceConfig();
        dataSourceConfig.setUrl(this.db_url);
        dataSourceConfig.setDriverName(this.db_driver_name);
        dataSourceConfig.setUsername(this.db_user_name);
        dataSourceConfig.setPassword(this.db_pass_word);
        // 包配置 文件生成路径
        PackageConfig packageConfig = new PackageConfig();
        packageConfig.setParent(page);
        packageConfig.setEntity("domain");
        packageConfig.setMapper("mapper");
        packageConfig.setService("service");
        packageConfig.setServiceImpl("service.impl");
        packageConfig.setController("controller");
        //packageConfig.setXml("mapper.xml");
        // 策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setEntityBuilderModel(false);
        strategyConfig.setEntityLombokModel(true);
        strategyConfig.setEntityTableFieldAnnotationEnable(false);
        strategyConfig.setNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);
        strategyConfig.setSuperServiceClass(this.superServiceClass);
        strategyConfig.setSuperServiceImplClass(this.superServiceImplClass);
        strategyConfig.setRestControllerStyle(true);
        // 自定义配置
        InjectionConfig injectionConfig = new InjectionConfig() {
            @Override
            public void initMap() {
            }
        };
        String template = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();
        focList.add(new FileOutConfig(template) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + "/src/main/resources/mapper/" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        injectionConfig.setFileOutConfigList(focList);
        // 设置要创建的表 不设置就是全量生成
        strategyConfig.setInclude(tables);
        // 模板配置
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        autoGenerator.setGlobalConfig(globalConfig);
        autoGenerator.setDataSource(dataSourceConfig);
        autoGenerator.setPackageInfo(packageConfig);
        autoGenerator.setStrategy(strategyConfig);
        autoGenerator.setCfg(injectionConfig);
        autoGenerator.setTemplate(templateConfig);
        autoGenerator.setTemplateEngine(new FreemarkerTemplateEngine());
        autoGenerator.execute();
    }

}
