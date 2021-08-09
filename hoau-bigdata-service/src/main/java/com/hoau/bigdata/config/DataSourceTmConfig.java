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
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
@MapperScan(basePackages = DataSourceTmConfig.TMMAPPERPACKAGE, sqlSessionFactoryRef = "tmSqlSessionFactory")
public class DataSourceTmConfig {

    static final String TMMAPPERPACKAGE = "com.hoau.bigdata.mapper.tm";
    static final String TMMAPPERLOCATION = "classpath:com/hoau/bigdata/mapper/tm/*.xml";
    static final String TMALIASESPACKAGE = "com.hoau.bigdata.entity.tm";

    @Bean(name = "dataSourceTm")
    @ConfigurationProperties("spring.datasource.druid.tm")

    public DataSource dataSource() {
        return DruidDataSourceBuilder.create().build();
    }

    @Bean(name = "tmTransactionManager")

    public DataSourceTransactionManager masterTransactionManager(@Qualifier("dataSourceTm") DataSource dataSourceOne) {
        return new DataSourceTransactionManager(dataSourceOne);
    }

    @Bean(name = "tmSqlSessionFactory")

    public SqlSessionFactory masterSqlSessionFactory(@Qualifier("dataSourceTm") DataSource dataSourceOne) throws Exception {
        //解决myBatis下 不能嵌套jar文件的问题
        VFS.addImplClass(SpringBootVFS.class);

        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(dataSourceOne);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(TMMAPPERLOCATION));
        sessionFactory.setTypeAliasesPackage(TMALIASESPACKAGE);
        return sessionFactory.getObject();
    }
}
