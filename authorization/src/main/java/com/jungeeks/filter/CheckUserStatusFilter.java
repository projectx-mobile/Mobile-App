package com.jungeeks.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jungeeks.entity.NoSecureUrl;
import com.jungeeks.security.entity.SecurityUserFirebase;
import com.jungeeks.security.service.AuthorizationService;
import com.jungeeks.service.SecurityService;
import com.jungeeks.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Order(SecurityProperties.DEFAULT_FILTER_ORDER + 4)
@Component
public class CheckUserStatusFilter implements Filter {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private SecurityService securityService;

    @Autowired
    private NoSecureUrl noSecureUrl;

    @Autowired
    @Qualifier("auth_userService")
    private UserService userService;

    private static final String ERROR_MESSAGE = "User deleted";
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        SecurityUserFirebase authorizationServiceUser = securityService.getUser();

        String s = ((HttpServletRequest) request).getRequestURL().toString();
        boolean exist = false;
        for (String url : noSecureUrl.getUrl()) {
            url = url.replace("/**", "");
            if (s.contains(url)) {
                exist = true;
                break;
            }
        }
        if (!exist){
            if (userService.checkUserStatus(authorizationServiceUser)) {
                HttpServletResponse httpResponse = (HttpServletResponse) response;
                Map<String, Object> errorObject = new HashMap<>();
                int errorCode = 403;
                errorObject.put("message", ERROR_MESSAGE);
                errorObject.put("error", HttpStatus.UNAUTHORIZED);
                errorObject.put("code", errorCode);
                errorObject.put("timestamp", new Timestamp(new Date().getTime()));
                httpResponse.setContentType(CONTENT_TYPE);
                httpResponse.setStatus(errorCode);
                httpResponse.getWriter().write(objectMapper.writeValueAsString(errorObject));
            } else {
                chain.doFilter(request, response);
            }
        }else {
            chain.doFilter(request, response);
        }


    }
}
