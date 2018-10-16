package cn.zyblogs.example.config;

import cn.zyblogs.example.threadlocal.HttpFilter;
import cn.zyblogs.example.threadlocal.HttpInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Title: Config.java
 * @Package cn.zyblogs.example.config
 * @Description: TODO
 * @Author ZhangYB
 * @Version V1.0
 */
@Configuration
public class Config implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean httpFilter() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        // 注册过滤器 拦截
        registrationBean.setFilter(new HttpFilter());
        // 拦截的路径
        registrationBean.addUrlPatterns("/threadLocal/*");
        return registrationBean;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor()).addPathPatterns("/**");

    }
}
