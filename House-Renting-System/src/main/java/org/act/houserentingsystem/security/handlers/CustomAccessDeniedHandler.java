package org.act.houserentingsystem.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.act.houserentingsystem.dto.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request,
                       HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .success(false)
                .message("Forbidden: " + accessDeniedException.getMessage())
                .data(null)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN); // 403

        String json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
