package com.dazt.springsecurity.config.filter;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class RequestValidationAfterFilter extends GenericFilterBean {

    private final Logger LOG = Logger.getLogger(RequestValidationAfterFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("---RequestValidationAfterFilter---");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            LOG.info("User: " + authentication.getName() + " is successfully authenticated and "
                    + "has the authorities " + authentication.getAuthorities().toString());
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
