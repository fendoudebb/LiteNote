package dev.z.blog.filter.security;

import dev.z.blog.infra.Cache;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StreamUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ReplayAttacksFilter extends OncePerRequestFilter {

    private static final String X_CA_Key = "X-Ca-Key";

    private static final String X_CA_TIMESTAMP = "X-Ca-Timestamp";

    private static final String X_CA_NONCE = "X-Ca-Nonce";

    private static final String X_CA_SIGNATURE = "X-Ca-Signature";

    private static final String X_CA_SIGNATURE_METHOD = "X-Ca-Signature-Method";

    private static final String X_CA_SIGNATURE_HEADERS = "X-Ca-Signature-Headers";

    private static final long EXPIRE_MILLIS = 60_000;

    private final Cache cache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String key = request.getHeader(X_CA_Key);
            String timestamp = request.getHeader(X_CA_TIMESTAMP);
            String nonce = request.getHeader(X_CA_NONCE);
            String signature = request.getHeader(X_CA_SIGNATURE);
            String signatureMethod = request.getHeader(X_CA_SIGNATURE_METHOD);
            String signatureHeaders = request.getHeader(X_CA_SIGNATURE_HEADERS);

            if (ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(nonce) || ObjectUtils.isEmpty(signature)) {
                log.error("Replay Attacks[{}] Invalid Http Headers: [{}, {}, {}], params: {}", request.getRequestURI(), timestamp, nonce, signature, StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8).replaceAll(System.lineSeparator(),""));
                throw new ReplayAttacksException();
            }

            long ts = Long.parseLong(timestamp);
            long currentTs = System.currentTimeMillis();
            long diff = currentTs - ts;
            if (Math.abs(diff) > EXPIRE_MILLIS) {
                log.error("Replay Attacks[{}] timestamp[{}] diff[{}] is out of scope[{}], params: {}", request.getRequestURI(), timestamp, diff, EXPIRE_MILLIS, StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8).replaceAll(System.lineSeparator(),""));
                throw new ReplayAttacksException();
            }

            String text = String.join("", timestamp, nonce);
            String calcSign = DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
            if (!Objects.equals(signature, calcSign)) {
                log.error("Replay Attacks[{}] Invalid Signature[{}], Raw text: {}, Calc Sign: {}, params: {}", request.getRequestURI(), signature, text, calcSign, StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8).replaceAll(System.lineSeparator(),""));
                throw new ReplayAttacksException();
            }

            Object value = cache.get(text);
            if (Objects.nonNull(value)) {
                log.error("Replay Attacks[{}] Exists Nonce[{}], Timestamp is {}", request.getRequestURI(), nonce, value);
                throw new ReplayAttacksException();
            }
            cache.put(Cache.Prefix.REPLAY_ATTACKS + signature, timestamp, EXPIRE_MILLIS, TimeUnit.MILLISECONDS);
            filterChain.doFilter(request, response);
        } catch (ReplayAttacksException | NumberFormatException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
        }
    }
}
