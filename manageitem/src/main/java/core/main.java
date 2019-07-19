package core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;


@ComponentScans(value = {@ComponentScan(value = "core")})
@SpringBootApplication
@EnableEurekaClient  //�����Զ�ע��
public class main {

}
