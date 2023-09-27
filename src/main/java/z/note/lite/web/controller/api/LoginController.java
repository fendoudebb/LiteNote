package z.note.lite.web.controller.api;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.infra.annotation.RateLimiter;
import z.note.lite.service.api.CaptchaService;
import z.note.lite.service.api.LoginService;
import z.note.lite.web.Endpoint;
import z.note.lite.web.advice.cipher.annotation.Decode;
import z.note.lite.web.advice.cipher.annotation.Encode;
import z.note.lite.web.http.request.Identity;
import z.note.lite.web.http.response.Credentials;

@Slf4j
@RestController
public class LoginController {

    @Resource
    private LoginService loginService;

    @Resource
    private CaptchaService captchaService;

    @Encode
    @RateLimiter
    @PostMapping(value = Endpoint.Api.LOGIN) // /api/login
    public Credentials login(@Decode @Valid @RequestBody Identity identity) {
        log.info("login request body: {}", identity);
        return loginService.login(identity);
    }

    @RateLimiter
    @GetMapping(value = Endpoint.Api.CAPTCHA) // /api/captcha
    public void captcha(HttpSession session) {
        captchaService.produce(session.getId());
    }

    @GetMapping(value = Endpoint.Api.LOGOUT) // /api/logout
    public void logout() {
        loginService.logout();
    }

}
