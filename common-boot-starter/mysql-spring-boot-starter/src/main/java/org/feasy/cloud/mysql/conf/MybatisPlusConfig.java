package org.feasy.cloud.mysql.conf;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
@MapperScan(basePackages = {"org.feasy.cloud.**.mapper"})
public class MybatisPlusConfig {
    /**
     * 分页插件
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链 阻止恶意的全表更新删除
        //sqlParserList.add(new BlockAttackSqlParser());
        //page.setSqlParserList(sqlParserList);
        page.setOverflow(true);
        page.setDialectType("mysql");
        page.setSqlParserList(sqlParserList);
        return page;
    }
}
