package z.note.lite.web.filter.crawler;

import is.tagomor.woothee.Classifier;
import is.tagomor.woothee.crawler.Crawlers;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import z.note.lite.web.Endpoint;

import java.io.IOException;
import java.util.Objects;

public class CrawlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String ua = request.getHeader(HttpHeaders.USER_AGENT);
            String referer = request.getHeader(HttpHeaders.REFERER);
            if (!StringUtils.hasText(ua) || !StringUtils.hasText(referer)) {
                throw new CrawlerException();
            }
            if (Classifier.isCrawler(ua)) {
                throw new CrawlerException();
            }
            filterChain.doFilter(request, response);
        } catch (CrawlerException e) {
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
    }
}
