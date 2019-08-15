package feign.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

/**
 * Created by wangg on 2019/7/12.
 */
@Configuration
@EnableFeignClients(basePackages = "feign.client")
public class UdfFeignConfiguration {

}
