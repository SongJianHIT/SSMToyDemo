/**
 * @projectName Demo
 * @package tech.songjian.config
 * @className tech.songjian.config.JdbcConfig
 */
package tech.songjian.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

/**
 * JdbcConfig   配置数据源
 * @description
 * @author SongJian
 * @date 2022/11/25 15:53
 * @version
 */
public class JdbcConfig {

    @Value("${jdbc.driver}")
    private String driver;

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.username}")
    private String username;

    @Value("${jdbc.password}")
    private String password;


    /**
     * @title dataSource
     * @author SongJian
     * @updateTime 2022/11/25 15:59
     * @return: javax.sql.DataSource
     * @throws
     * @description 获取数据源，他的返回值应该被 Spring 管理
     */
    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

    /**
     * @title platformTransactionManager
     * @author SongJian
     * @param: dataSource
     * @updateTime 2022/11/25 16:02
     * @return: org.springframework.transaction.PlatformTransactionManager
     * @throws
     * @description 平台事务管理对象，他的返回值应该被 Spring 管理
     */

    @Bean
    public PlatformTransactionManager platformTransactionManager(DataSource dataSource) {
        DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
        dataSourceTransactionManager.setDataSource(dataSource);
        return dataSourceTransactionManager;
    }
}
 
