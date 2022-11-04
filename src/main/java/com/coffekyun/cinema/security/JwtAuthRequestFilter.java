package com.coffekyun.cinema.security;

import com.coffekyun.cinema.config.JwtConfiguration;
import com.coffekyun.cinema.service.JwtService;
import com.coffekyun.cinema.util.JwtUtil;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.common.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthRequestFilter extends OncePerRequestFilter {

    private final JwtConfiguration jwtConfiguration;
    private final JwtUtil jwtUtil;

    private final JwtService jwtService;

    @Autowired
    public JwtAuthRequestFilter(JwtConfiguration jwtConfiguration, JwtUtil jwtUtil, JwtService jwtService) {
        this.jwtConfiguration = jwtConfiguration;
        this.jwtUtil = jwtUtil;
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        log.info("#calling do filter internal");
        String authorization = httpServletRequest.getHeader("Authorization");
        log.info("authorization {}", authorization );
        String token = null;
        String userName = null;

        if(authorization != null && authorization.startsWith("Bearer ")) {
            token = authorization.substring(7);
            log.info("token {} ", token);
            userName = jwtUtil.getUsernameFromToken(token);
            log.info("token {} ", userName);
        }

        if(null != userName && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails
                    = jwtService.loadUserByUsername(userName);

            log.info("user details {} with token {} ", userDetails.getUsername(), token);
            if(jwtUtil.validateToken(token,userDetails)) {
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken
                        = new UsernamePasswordAuthenticationToken(
                                userDetails,
                        null,
                        userDetails.getAuthorities());

                usernamePasswordAuthenticationToken.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                );

                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }

        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

}
