package com.example.ems.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final String REQUEST_ID_KEY = "requestId";
    private static final String REQUEST_ID_HEADER = "X-Request-Id";

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String requestId = resolveRequestId(request);
        MDC.put(REQUEST_ID_KEY, requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);

        long start = System.currentTimeMillis();
        String method = request.getMethod();
        String uri = request.getRequestURI();
        String query = request.getQueryString();
        String clientIp = getClientIp(request);
        String userAgent = request.getHeader("User-Agent");

        log.info("REQ START method={} uri={} query={} clientIp={} userAgent={}",
                method, uri, query == null ? "" : query, clientIp, userAgent == null ? "" : userAgent);

        try {
            filterChain.doFilter(request, response);
            long duration = System.currentTimeMillis() - start;
            log.info("REQ END method={} uri={} status={} durationMs={}",
                    method, uri, response.getStatus(), duration);
        } catch (Exception ex) {
            long duration = System.currentTimeMillis() - start;
            log.error("REQ FAIL method={} uri={} status={} durationMs={} message={}",
                    method, uri, response.getStatus(), duration, ex.getMessage(), ex);
            throw ex;
        } finally {
            MDC.clear();
        }
    }

    private String resolveRequestId(HttpServletRequest request) {
        String headerId = request.getHeader(REQUEST_ID_HEADER);
        if (headerId != null && !headerId.isBlank()) {
            return headerId;
        }
        return UUID.randomUUID().toString();
    }

    private String getClientIp(HttpServletRequest request) {
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor != null && !forwardedFor.isBlank()) {
            return forwardedFor.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
