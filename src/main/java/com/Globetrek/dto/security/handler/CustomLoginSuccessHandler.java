package com.Globetrek.dto.security.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    private static final String REDIRECT_PARAM = "redirect";
    private static final String DEFAULT_URL = "/countries";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        // 1) redirect 파라미터가 있으면 우선 사용
        String targetUrl = Optional.ofNullable(request.getParameter(REDIRECT_PARAM))
                                   .filter(s -> !s.isBlank())
                                   .orElse(null);

        // 2) SavedRequest 확인 (보통 SecurityContext 릴레이)
        if (targetUrl == null) {
            SavedRequest savedRequest =
                new HttpSessionRequestCache().getRequest(request, response);
            if (savedRequest != null) {
                targetUrl = savedRequest.getRedirectUrl();
            }
        }

        // 3) 위가 모두 null 이면 기본 URL
        if (targetUrl == null) {
            targetUrl = DEFAULT_URL;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }
}

