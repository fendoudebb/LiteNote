package z.lite.note.security.filter.impl;

import z.lite.note.security.filter.IdentityFilter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class SessionIdentityFilter extends OncePerRequestFilter implements IdentityFilter {

    private static final String ATTR = "token";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute(ATTR) instanceof Authentication authentication) {
            SecurityContextHolder.createEmptyContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
