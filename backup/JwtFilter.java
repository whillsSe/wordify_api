/*
package com.wordify.servlet;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;

import com.wordify.service.auth.JwtTokenValidator;

import io.jsonwebtoken.JwtException;

import java.io.IOException;
//@WebFilter(urlPatterns = "/*")  // すべてのURLパターンに適用するフィルター
public class JwtFilter implements Filter {

    private final JwtTokenValidator jwtTokenValidator = JwtTokenValidator.getInstance();

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) request;
        String authHeader = httpRequest.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);  // "Bearer "の部分を削除
            try {
                Integer userId = jwtTokenValidator.validateJwtToken(token);
                request.setAttribute("loginUser", userId);  // リクエスト属性にユーザIDを設定
            } catch (JwtException e) {
                throw new ServletException("Invalid token", e);
            }
        } else {
            throw new ServletException("Missing token");
        }

        chain.doFilter(request, response);  // フィルターチェーンを続行
    }

    @Override
    public void destroy() {
    }
}
*/