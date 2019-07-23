package core.spring.filter;


import core.exception.AccessDeniedException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("______________init ExceptionFilter___________");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            filterChain.doFilter(servletRequest,servletResponse);
        }catch (Exception e){
//            e.printStackTrace();
            if(e.getCause() instanceof AccessDeniedException){
//                servletResponse.getWriter().println("没有权限");

            }
        }
    }

    @Override
    public void destroy() {

    }
}
