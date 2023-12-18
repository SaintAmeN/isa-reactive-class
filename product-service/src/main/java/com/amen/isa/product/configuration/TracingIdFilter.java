package com.amen.isa.product.configuration;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.amen.isa.component.configuration.Constants.REQUEST_ID_HEADER_NAME;

@Slf4j
@Component
@WebFilter("/**")
public class TracingIdFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("Request id arrived: {}", req.getHeader(REQUEST_ID_HEADER_NAME));

        // TODO: add to threadlocal lub innego magazynu

        chain.doFilter(request, response);
    }
}
