package acct.aop;

import core.core.ReturnCode;
import core.exception.AccessDeniedException;
import core.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

@Aspect
@Service
public class RpcAOP {
    @Pointcut("within(acct.web.rest..*)")
    public void test() {
    }
    @Before("test()")
    public void needLoginCheck(JoinPoint joinPoint) throws Throwable{
        System.out.println("开始校验111");
    }
    @After("test()")
    public void test2(JoinPoint joinPoint) throws Throwable{
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = attributes.getResponse();
//        PrintWriter writer = response.getWriter();
//        writer.append("aaa");
//        writer.flush();
//        writer.close();
//        attributes.getResponse().setContentType("text/html;charset=UTF-8");
//        attributes.getResponse().setCharacterEncoding("UTF-8");
    }
}

