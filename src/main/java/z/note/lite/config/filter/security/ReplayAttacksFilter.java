package z.note.lite.config.filter.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.util.ObjectUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import z.note.lite.controller.Endpoint;
import z.note.lite.infra.Cache;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
public class ReplayAttacksFilter extends OncePerRequestFilter {

    private static final String X_CA_KEY = "X-Ca-Key";

    private static final String X_CA_TIMESTAMP = "X-Ca-Timestamp";

    private static final String X_CA_NONCE = "X-Ca-Nonce";

    private static final String X_CA_SIGNATURE = "X-Ca-Signature";

    private static final String X_CA_SIGNATURE_METHOD = "X-Ca-Signature-Method";

    private static final String X_CA_SIGNATURE_HEADERS = "X-Ca-Signature-Headers";

    private static final String SIGNATURE_METHOD_BASE64 = "BASE64";

    private static final long EXPIRE_MILLIS = 60_000;

    private final Cache cache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (Objects.equals(request.getRequestURI(), Endpoint.Api.CAPTCHA) || Objects.equals(request.getRequestURI(), Endpoint.Api.SSE)) {
            filterChain.doFilter(request, response);
            return;
        }
        try {
            String key = request.getHeader(X_CA_KEY);
            String timestamp = request.getHeader(X_CA_TIMESTAMP);
            String nonce = request.getHeader(X_CA_NONCE);
            String signature = request.getHeader(X_CA_SIGNATURE);
            String signatureMethod = request.getHeader(X_CA_SIGNATURE_METHOD);
            String signatureHeaders = request.getHeader(X_CA_SIGNATURE_HEADERS);

            if (ObjectUtils.isEmpty(timestamp) || ObjectUtils.isEmpty(nonce) || ObjectUtils.isEmpty(signature)) {
                log.error("Replay Attacks[{}] Invalid Http Headers: [{}, {}, {}]", request.getRequestURI(), timestamp, nonce, signature);
                throw new ReplayAttacksException();
            }

            long ts = Long.parseLong(timestamp);
            long currentTs = System.currentTimeMillis();
            long diff = currentTs - ts;
            if (Math.abs(diff) > EXPIRE_MILLIS) {
                log.error("Replay Attacks[{}] timestamp[{}] diff[{}] is out of scope[{}]", request.getRequestURI(), timestamp, diff, EXPIRE_MILLIS);
                throw new ReplayAttacksException();
            }

            String text = String.join("", timestamp, nonce);
            String calcSign;
            if (signatureMethod.equals(SIGNATURE_METHOD_BASE64)) {
                calcSign = Base64.getEncoder().encodeToString(text.getBytes(StandardCharsets.UTF_8));
            } else {
                calcSign = null;
            }
//            String calcSign = DigestUtils.md5DigestAsHex(text.getBytes(StandardCharsets.UTF_8));
            if (!Objects.equals(signature, calcSign)) {
                log.error("Replay Attacks[{}] Invalid Signature[{}], Raw text: {}, Calc Sign: {}", request.getRequestURI(), signature, text, calcSign);
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
