package z.note.lite.infra.lib.captcha;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;

public class ArithmeticCaptcha extends ArithmeticCaptchaAbstract {

    public ArithmeticCaptcha() {
    }

    public ArithmeticCaptcha(int width, int height) {
        this();
        setWidth(width);
        setHeight(height);
    }

    public ArithmeticCaptcha(int width, int height, int len) {
        this(width, height);
        setLen(len);
    }

    public ArithmeticCaptcha(int width, int height, int len, Font font) {
        this(width, height, len);
        setFont(font);
    }

    @Override
    public boolean out(OutputStream out) {
        checkAlpha();
        return graphicsImage(getArithmeticString().toCharArray(), out);
    }

    @Override
    public String toBase64() {
        return toBase64("data:image/png;base64,");
    }

    private boolean graphicsImage(char[] strs, OutputStream out) {
        try (out) {
            BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            drawOval(2, g2d);
            g2d.setFont(getFont());
            FontMetrics fontMetrics = g2d.getFontMetrics();
            int fW = width / strs.length;
            int fSp = (fW - (int) fontMetrics.getStringBounds("8", g2d).getWidth()) / 2;
            for (int i = 0; i < strs.length; i++) {
                g2d.setColor(color());
                int fY = height - ((height - (int) fontMetrics.getStringBounds(String.valueOf(strs[i]), g2d).getHeight()) >> 1);
                g2d.drawString(String.valueOf(strs[i]), i * fW + fSp + 3, fY - 3);
            }
            g2d.dispose();
            ImageIO.write(bi, "png", out);
            out.flush();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
