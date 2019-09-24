package acct.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;

@Configuration
public class StarsFilterConfiguration {

    @Configuration
    public static class CorsAllowedFilterConfiguration {
        @Bean
        public FilterRegistrationBean corsAllowedFilterRegistration() {
            FilterRegistrationBean registration = new FilterRegistrationBean();
            final Filter securityFilter = corsAllowedFilter();
            registration.setFilter(securityFilter);
            registration.addUrlPatterns("/*");
            registration.setName("corsAllowedFilter");
            registration.setOrder(-102);
            return registration;
        }

        @Bean()
        public Filter corsAllowedFilter() {
            return new CorsAllowedFilter();
        }
    }

    @Configuration
    public static class TestFilterConfigure{
        @Bean
        public FilterRegistrationBean testFilterConfigure(){
            FilterRegistrationBean registrationBean = new FilterRegistrationBean();
            final Filter testFiler = testFilter();
            registrationBean.setFilter(testFiler);
            registrationBean.addUrlPatterns("/*");
            registrationBean.setName("testFilter");
            registrationBean.setOrder(-103);
            return registrationBean;
        }
        @Bean()
        public Filter testFilter() {
            return new TestFilter();
        }
    }
}
