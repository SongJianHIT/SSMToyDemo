/**
 * @projectName Demo
 * @package tech.songjian.config
 * @className tech.songjian.config.ServletConfig
 */
package tech.songjian.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

/**
 * ServletConfig
 * @description web容器配置类
 * @author SongJian
 * @date 2022/11/25 16:11
 * @version
 */
public class ServletConfig extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {SpringConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] {SpringMvcConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        return new String[] {"/"};
    }
}
 
