/**
 * @projectName Demo
 * @package tech.songjian.config
 * @className tech.songjian.config.MyBatisConfig
 */
package tech.songjian.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * MyBatisConfig
 * @description MyBatis 配置类
 * @author SongJian
 * @date 2022/11/25 16:03
 * @version
 */
public class MyBatisConfig {

    /**
     * @title
     * @author SongJian
     * @updateTime 2022/11/25 16:19
     * @throws
     * @description 定义bean，SqlSessionFactoryBean，用于产生SqlSessionFactory对象
     */

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage("tech.songjian.domain");
        return sqlSessionFactoryBean;
    }

    /**
     * @title mapperScannerConfigurer
     * @author SongJian
     * @updateTime 2022/11/25 16:19
     * @return: org.mybatis.spring.mapper.MapperScannerConfigurer
     * @throws
     * @description  定义bean，返回MapperScannerConfigurer对象
     */

    @Bean
    public MapperScannerConfigurer mapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("tech.songjian.dao");
        return mapperScannerConfigurer;
    }
}
 
