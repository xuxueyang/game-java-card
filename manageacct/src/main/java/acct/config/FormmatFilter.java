package acct.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class FormmatFilter {
    /**
     * FastJson返回
     * @param
     */
    @Bean
    public HttpMessageConverters configureMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
            //是否格式化返回Json
            SerializerFeature.PrettyFormat
        );
        //处理中文乱码问题
        List<MediaType> fastMediaTypes = new ArrayList<>();
        fastMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        fastConverter.setSupportedMediaTypes(fastMediaTypes);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        return  new HttpMessageConverters(fastConverter);
    }
//    @JSONField(format ="yyyy-MM-dd HH:mm:ss" )
//    https://zhuanlan.zhihu.com/p/32339266
//    DisableCheckSpecialChar：一个对象的字符串属性中如果有特殊字符如双引号，将会在转成json时带有反斜杠转义符。如果不需要转义，可以使用这个属性。默认为false
//
//    QuoteFieldNames———-输出key时是否使用双引号,默认为true
//
//    WriteMapNullValue——–是否输出值为null的字段,默认为false
//
//    WriteNullNumberAsZero—-数值字段如果为null,输出为0,而非null
//
//    WriteNullListAsEmpty—–List字段如果为null,输出为[],而非null
//
//    WriteNullStringAsEmpty—字符类型字段如果为null,输出为”“,而非null
//
//    WriteNullBooleanAsFalse–Boolean字段如果为null,输出为false,而非null
}
