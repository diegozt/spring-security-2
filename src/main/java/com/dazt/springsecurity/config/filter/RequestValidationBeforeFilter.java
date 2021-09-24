package com.dazt.springsecurity.config.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;

public class RequestValidationBeforeFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("Test the filter: ---RequestValidationBeforeFilter---");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
