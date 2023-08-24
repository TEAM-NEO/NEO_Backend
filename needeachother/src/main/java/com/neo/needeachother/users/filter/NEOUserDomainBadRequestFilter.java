package com.neo.needeachother.users.filter;

import com.neo.needeachother.common.filter.NEORereadableRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;

public class NEOUserDomainBadRequestFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        NEORereadableRequestWrapper rereadableRequestWrapper = new NEORereadableRequestWrapper((HttpServletRequest) request);
        chain.doFilter(rereadableRequestWrapper, response);
    }
}
