package org.dara.walletservice;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;
import java.util.regex.Pattern;

@Component
public class CorrelationIdFilter extends OncePerRequestFilter {

    private static final String HEADER_NAME = "X-Correlation-Id";
    private static final String MDC_KEY = "correlationId";

    private static final Pattern VALID_ID = Pattern.compile("[a-zA-Z0-9._-]{1,64}");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String suppliedId = request.getHeader(HEADER_NAME);

        String correlationId =
                suppliedId != null && VALID_ID.matcher(suppliedId).matches()
                        ? suppliedId
                        : UUID.randomUUID().toString();

        MDC.put(MDC_KEY, correlationId);
        response.setHeader(HEADER_NAME, correlationId);

        try {
            filterChain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }
}
