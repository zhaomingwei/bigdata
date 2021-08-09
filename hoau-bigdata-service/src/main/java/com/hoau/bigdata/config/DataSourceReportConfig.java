package com.hoau.bigdata.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.io.VFS;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = DataSourceReportConfig.REPORTMAPPERPACKAGE, sqlSessionFactoryRef = "reportSqlSessionFactory")
public class DataSourceReportConfig {

    static final String REPORTMAPPERPACKAGE = "com.hoau.bigdata.mapper.report";
    static final String REPORTMAPPERLOCATION = "classpath:com/hoau/bigdata/mapper/report/*.xml";
    static final String REPORTALIASESPACKAGE = "com.hoau.bigdata.entity.report";

    @Bean(name = "dataSourceReport")
    @ConfigurationProperties("spring.datasource.druid.report")
    @Primary
    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "reportTransactionManager")
    @Primary
    public DataSourceTransactionManager masterTransactionManager(@Qualifier("dataSourceReport") DataSource dataSourceOne) {
        return new DataSourceTransactionManager(dataSourceOne);
    }

    @Bean(name = "reportSqlSessionFactory")
    @Primary
    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataSourceReport") DataSource dataSourceOne) throws Exception {
        //解决myBatis下 不能嵌套jar文件的问题
        VFS.addImplClass(SpringBootVFS.class);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceOne);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(REPORTMAPPERLOCATION));
        sessionFactory.setTypeAliasesPackage(REPORTALIASESPACKAGE);
        return sessionFactory.getObject();
    }
}
