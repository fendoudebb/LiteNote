package z.note.lite.service.api.impl;

import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import z.note.lite.infra.Cache;
import z.note.lite.lib.captcha.ArithmeticCaptcha;
import z.note.lite.service.api.CaptchaService;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class GraphicCaptchaService implements CaptchaService {

    public static final int WIDTH = 110;

    public static final int HEIGHT = 38;

    private final Cache cache;

    @Override
    public void produce(String key) {
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            HttpServletResponse response = attributes.getResponse();

            int width = ServletRequestUtils.getIntParameter(request, "width", WIDTH);
            int height = ServletRequestUtils.getIntParameter(request, "height", HEIGHT);

            int random = ThreadLocalRandom.current().nextInt(0, 100);
            int expireSeconds = 60;
            response.setIntHeader("Refresh", expireSeconds);
//            response.setDateHeader("Expires", -1);
            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Pragma", "no-cache");
            response.setContentType(MediaType.IMAGE_PNG_VALUE);
            Captcha captcha;
            switch (random % 3) {
                case 1 -> captcha = new SpecCaptcha(width, height, 4);
                case 2 -> captcha = new ArithmeticCaptcha(width, height);
                default -> {
                    response.setContentType(MediaType.IMAGE_GIF_VALUE);
                    captcha = new GifCaptcha(width, height, 4);
                }
            }
            String code = captcha.text().toLowerCase();
            cache.put(Cache.Prefix.CAPTCHA + request.getSession().getId(), code, expireSeconds, TimeUnit.SECONDS);
            captcha.out(response.getOutputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
