package com.neo.needeachother.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.neo.needeachother.common.response.NEOFinalErrorResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class NEOServletResponseWriter {

    private final ObjectMapper objectMapper;

    public void initEncoding(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
    }

    public void writeResponseWithBodyAndStatusCode(HttpServletResponse response, Object body, int sc) throws IOException {
        initEncoding(response);
        response.setStatus(sc);
        response.getWriter().write(objectMapper.writeValueAsString(body));
        response.getWriter().flush();
    }

    public void writeResponseWithErrorAndStatusCode(HttpServletResponse response, NEOFinalErrorResponse errorResponse, int sc) throws IOException {
        writeResponseWithBodyAndStatusCode(response, errorResponse, sc);
    }
}
