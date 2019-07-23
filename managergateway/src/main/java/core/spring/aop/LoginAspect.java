package core.spring.aop;

import core.core.RequestDTO;
import core.core.ReturnCode;
import core.exception.AccessDeniedException;
import core.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Service
public class LoginAspect {
    @Autowired
    private AcctRpcClient acctRpcClient;

//    @Pointcut("")
//    public void test() {
//    }
//    @Around("test()")
//    public Object testAs(ProceedingJoinPoint joinPoint) throws Throwable {
//        System.out.println("________________");
//        Object proceed = joinPoint.proceed();
//        return proceed;
//    }
    @Pointcut("within(@core.spring.aop.NeedLoginAspect *)")
    public void needLogin() {
    }

    @Before("needLogin()")
    public void needLoginCheck(JoinPoint joinPoint) throws Throwable{
        System.out.println("开始校验");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        javax.servlet.http.HttpServletRequest request = attributes.getRequest();
//        boolean ad =  acctRpcClient.checkToken(new RequestDTO<Object>());
        boolean test = acctRpcClient.test();
        String header = attributes.getRequest().getHeader(Protocol.TOKEN);
        if(StringUtils.isBlank(header)){
            //return new ResponseEntity<ReturnResultDTO<?>>(new ReturnResultDTO(ReturnCode.ERROR_USER_HAS_LOGOUT, null), HttpStatus.OK);
            throw new AccessDeniedException(ReturnCode.ERROR_FIELD_EMPTY,"未携带token");
        }
        //如果不为null，需要rpc校验
//        boolean loginhas =  acctRpcClient.checkToken(header);
        boolean loginhas = true;
        System.out.println("开始校验：" + loginhas);
        if(!loginhas){
            throw new AccessDeniedException(ReturnCode.ERROR_USER_HAS_LOGOUT,"已登出");
        }
//            return new ResponseEntity<ReturnResultDTO<?>>(new ReturnResultDTO(ReturnCode.ERROR_USER_HAS_LOGOUT, null), HttpStatus.OK);
//        return  joinPoint.proceed();
    }
}
