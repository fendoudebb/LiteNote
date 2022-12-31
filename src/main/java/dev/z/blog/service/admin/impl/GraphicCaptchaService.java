package dev.z.blog.service.admin.impl;

import dev.z.blog.infra.Cache;
import dev.z.blog.service.admin.CaptchaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class GraphicCaptchaService implements CaptchaService {

    public static final int WIDTH = 120;

    public static final int HEIGHT = 40;

    private final Cache cache;

    @Override
    public void produce(String username, String code) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

        int width = ServletRequestUtils.getIntParameter(request, "width", WIDTH);
        int height = ServletRequestUtils.getIntParameter(request, "height", HEIGHT);

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics graphics = image.getGraphics();
        Color color = new Color(240, 240, 140);
        graphics.setColor(color);
        graphics.fillRect(0, 0, width, height);
        char[] chars = code.toCharArray();
        Random r = new Random();
        Font axesFont = new Font("宋体", Font.ITALIC, 32);
        for (int i = 0; i < chars.length; i++) {
            graphics.setFont(axesFont);
            graphics.setColor(new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255)));
            graphics.drawString(chars[i] + "", i * 20, 20 + r.nextInt(20));
            graphics.drawLine(r.nextInt(width), r.nextInt(40), r.nextInt(120), r.nextInt(40));
        }
        graphics.dispose();

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setHeader("refresh", "60");
        response.setDateHeader("expires", 0);
        response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        try {
            ImageIO.write(image, "jpeg", response.getOutputStream());
            cache.put(Cache.Prefix.CAPTCHA + username, code, 5, TimeUnit.MINUTES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
