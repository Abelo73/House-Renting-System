package org.act.houserentingsystem.security.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.act.houserentingsystem.dto.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        ApiResponse<?> apiResponse = ApiResponse.builder()
                .success(false)
                .message("Unauthorized: " + authException.getMessage())
                .data(null)
                .build();

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); // 401

        String json = objectMapper.writeValueAsString(apiResponse);
        response.getWriter().write(json);
    }
}
