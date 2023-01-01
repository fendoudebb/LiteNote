package z.lite.note.controller.api;

import z.lite.note.advice.cipher.annotation.Decode;
import z.lite.note.advice.cipher.annotation.Encode;
import z.lite.note.dto.request.Identity;
import z.lite.note.dto.response.Credentials;
import z.lite.note.service.admin.CaptchaService;
import z.lite.note.service.admin.LoginService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    private final CaptchaService captchaService;

    @PostMapping("/login")
    @Encode
    public Credentials login(@Decode @Valid @RequestBody Identity identity) {
        log.info("login request body: {}" , identity);
        return loginService.login(identity);
    }

    @GetMapping("/captcha")
    public void captcha(@RequestParam String username) {
        String code = captchaService.verificationCode();
        log.info("Captcha code: {}", code);
        captchaService.produce(username, code);
    }

}
