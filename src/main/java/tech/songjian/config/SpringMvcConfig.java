/**
 * @projectName Demo
 * @package tech.songjian.config
 * @className tech.songjian.config.SpringMvcConfig
 */
package tech.songjian.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * SpringMvcConfig
 * @description
 * @author SongJian
 * @date 2022/11/25 15:53
 * @version
 */
@Configuration
@ComponentScan({"tech.songjian.controller","tech.songjian.config"})
@EnableWebMvc
public class SpringMvcConfig {
}