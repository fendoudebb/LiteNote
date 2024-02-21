package z.note.lite.infra.lib.captcha;

import com.wf.captcha.base.Captcha;
import lombok.Getter;
import lombok.Setter;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;

@Getter
@Setter
public abstract class ArithmeticCaptchaAbstract extends Captcha {

    private String arithmeticString;

    public ArithmeticCaptchaAbstract() {
        setLen(2);
    }

    @Override
    protected char[] alphas() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(num(10));
            if (i < len - 1) {
                int type = num(1, 4);
                if (type == 1) {
                    sb.append("+");
                } else if (type == 2) {
                    sb.append("-");
                } else if (type == 3) {
                    sb.append("x");
                }
            }
        }
        SpelExpressionParser parser = new SpelExpressionParser();
        Expression expression = parser.parseExpression(sb.toString().replaceAll("x", "*"));
        chars = String.valueOf(expression.getValue());
        sb.append("=?");
        arithmeticString = sb.toString();
        return chars.toCharArray();
    }

}
