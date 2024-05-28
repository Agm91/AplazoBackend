package com.aplazo.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request,
            HttpServletResponse response, Object handler) throws Exception {
                logger.info("Incoming request: {} {}", request.getMethod(), request.getRequestURI());
                return true;
    }
}
