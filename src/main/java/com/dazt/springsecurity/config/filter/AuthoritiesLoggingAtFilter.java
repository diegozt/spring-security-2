package com.dazt.springsecurity.config.filter;

import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.*;
import java.io.IOException;
import java.util.logging.Logger;

public class AuthoritiesLoggingAtFilter extends GenericFilterBean {

    private final Logger LOG = Logger.getLogger(AuthoritiesLoggingAtFilter.class.getName());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        LOG.info("Authentication Validation is in progress");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
