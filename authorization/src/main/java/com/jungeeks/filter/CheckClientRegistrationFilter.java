package com.jungeeks.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jungeeks.entity.NoSecureUrl;
import com.jungeeks.exception.RegistrationFailedException;
import com.jungeeks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 5)
@Component
public class CheckClientRegistrationFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private NoSecureUrl noSecureUrl;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        String s = ((HttpServletRequest) request).getRequestURL().toString();

        boolean exist = false;
        for (String url : noSecureUrl.getUrl()) {
            url = url.replace("/**", "");
            if (s.contains(url)) {
                exist = true;
                break;
            }
        }

        if (!exist) {
            String registration_token = request.getParameter("registration_token");
            if ((Objects.isNull(registration_token) || registration_token.isBlank()) && !userService.checkUserByContainsRegistrationToken()) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                Map<String, Object> errorObject = new HashMap<>();
                int errorCode = 401;
                errorObject.put("message", "App not registered");
                errorObject.put("error", HttpStatus.UNAUTHORIZED);
                errorObject.put("code", errorCode);
                errorObject.put("timestamp", new Timestamp(new Date().getTime()));
                httpResponse.setContentType("application/json;charset=UTF-8");
                httpResponse.setStatus(errorCode);
                httpResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
            }else {
                chain.doFilter(request,response);
            }
        }
    }

}
