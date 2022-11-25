/**
 * @projectName Demo
 * @package tech.songjian.config
 * @className tech.songjian.config.SpringConfig
 */
package tech.songjian.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * SpringConfig
 * @description Spring主配置文件
 * @author SongJian
 * @date 2022/11/25 15:08
 * @version
 */
@Configuration
@ComponentScan("tech.songjian.service")
@PropertySource("classpath:jdbc.properties")
@Import({JdbcConfig.class,MyBatisConfig.class})
@EnableTransactionManagement
public class SpringConfig {
}
 
