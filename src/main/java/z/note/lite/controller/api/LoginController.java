package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import z.note.lite.advice.cipher.annotation.Decode;
import z.note.lite.advice.cipher.annotation.Encode;
import z.note.lite.constant.mvc.Endpoint;
import z.note.lite.dto.request.Identity;
import z.note.lite.dto.response.Credentials;
import z.note.lite.infra.annotation.RateLimiter;
import z.note.lite.service.api.CaptchaService;
import z.note.lite.service.api.LoginService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        log.info("login request body: {}" , identity);
        return loginService.login(identity);
    }

    @RateLimiter
    @GetMapping(value = Endpoint.Api.CAPTCHA) // /api/captcha
    public void captcha(@RequestParam String username) {
        captchaService.produce(username);
    }

}
