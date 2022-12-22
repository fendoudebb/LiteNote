package dev.z.blog.security.filter.impl;

import dev.z.blog.security.filter.IdentityFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtIdentityFilter extends OncePerRequestFilter implements IdentityFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // TODO jwt identity filter
        filterChain.doFilter(request, response);
    }
}
