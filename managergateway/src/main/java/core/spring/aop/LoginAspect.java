package core.spring.aop;

import core.core.RequestDTO;
import core.core.ReturnCode;
import core.core.ReturnResultDTO;
import core.exception.AccessDeniedException;
import core.protocol.Protocol;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
        String header = attributes.getRequest().getHeader(Protocol.TOKEN);
        if(StringUtils.isBlank(header)){
            //return new ResponseEntity<ReturnResultDTO<?>>(new ReturnResultDTO(ReturnCode.ERROR_USER_HAS_LOGOUT, null), HttpStatus.OK);
            throw new AccessDeniedException(ReturnCode.ERROR_FIELD_EMPTY,"未携带token");
        }
        //如果不为null，需要rpc校验
//        boolean loginhas =  true;//acctRpcClient.checkToken(header);
//        System.out.println("开始校验：" + loginhas);
        RequestDTO dto = new RequestDTO();
        dto.setData(header);
        ResponseEntity responseEntity = acctRpcClient.checkToken(dto);
        ReturnResultDTO body = (ReturnResultDTO) responseEntity.getBody();
        if(body.getData()!=null&& body.getData() instanceof Boolean && (Boolean)body.getData()){
            System.out.println("校验成功");
        }else{
            throw new AccessDeniedException(ReturnCode.ERROR_USER_HAS_LOGOUT,"已登出");
        }
//            return new ResponseEntity<ReturnResultDTO<?>>(new ReturnResultDTO(ReturnCode.ERROR_USER_HAS_LOGOUT, null), HttpStatus.OK);
//        return  joinPoint.proceed();
    }
}
