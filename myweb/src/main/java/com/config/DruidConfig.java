package com.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: W.B.An
 * @Date: 2020/4/18(星期六)
 * @Time: 8:36
 */
@Configuration
public class DruidConfig {
    //注入druid配置
    @ConfigurationProperties(prefix = "spring.datasource")
    @Bean
    public DataSource Druid() {
        return new DruidDataSource();
    }

    //配置druid监控
    //配置一个管理后台的servlet
    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean bean =
                new ServletRegistrationBean(new StatViewServlet(),
                        "/druid/*");
        Map<String, String> initparams = new HashMap<>();
        initparams.put("loginUsername", "admin");
        initparams.put("loginPassword", "admin");
        bean.setInitParameters(initparams);
        return bean;
    }

    //配置一个web监控的过滤器 filter
    @Bean
    public FilterRegistrationBean webStatFilter() {
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.setFilter(new WebStatFilter());
        Map<String, String> initparams = new HashMap<>();
        initparams.put("exclusions", "*.js,*.css");
        bean.setInitParameters(initparams);
        bean.setUrlPatterns(Arrays.asList("/*"));
        return bean;
    }
}
