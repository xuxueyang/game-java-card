package core.spring.config;

import core.spring.filter.ExceptionFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class FilterConfiguration {
//    @Configuration
    public static class ExceptionFilterFilterConfiguration {
        @Bean
        public FilterRegistrationBean exceptionFilterRegistration() {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            final Filter securityFilter = exceptionFilter();
            registration.setFilter(securityFilter);
            registration.addUrlPatterns("/*");
            registration.setName("exceptionFilter");
            registration.setOrder(1);
            return registration;
        }

        @Bean()
        public Filter exceptionFilter() {
            return new ExceptionFilter();
        }
    }
}
