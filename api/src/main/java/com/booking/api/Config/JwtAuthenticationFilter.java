package com.booking.api.Config;

import com.booking.api.Service.AuthenticationService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;


import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    public JwtAuthenticationFilter(AuthenticationService authenticationService) {
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Using ContentCachingRequestWrapper to read the request body multiple times
        ContentCachingRequestWrapper cachingRequest = new ContentCachingRequestWrapper(request);

        // Proceed with the filter chain
        filterChain.doFilter(cachingRequest, response);

        // Extract the request body after it has been cached
        String body = new String(cachingRequest.getContentAsByteArray(), StandardCharsets.UTF_8);

        // Logic to get credentials from the body
        if (body != null && !body.isEmpty()) {
            // Assume you have a method to extract credentials from the body
            String username = extractUsername(body);
            String password = extractPassword(body);

            // Perform authentication or other logic
        }
    }

    // Example methods to extract credentials
    private String extractUsername(String body) {
        // Extract username from the body
        return body.split("username:")[1].split(",")[0].trim();
    }

    private String extractPassword(String body) {
        // Extract password from the body
        return body.split("password:")[1].split(",")[0].trim();
    }
}

