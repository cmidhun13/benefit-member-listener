package com.szells.membership.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.szells.membership.model.GenericResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author Riya Patel
 */
@Component
public class RestAuthenticationEntryPoint implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException ex) throws IOException {
        response.setStatus(HttpStatus.OK.value());
        OutputStream out = response.getOutputStream();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(out, new GenericResponse(false, HttpStatus.FORBIDDEN.value(), ex.getMessage(), null, null));
        out.flush();
    }
}
