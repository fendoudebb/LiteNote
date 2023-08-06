package z.note.lite.context;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.apache.catalina.connector.Request;
import org.apache.tomcat.util.http.parser.AcceptLanguage;
import org.apache.tomcat.util.http.parser.HttpParser;

import java.io.IOException;
import java.io.StringReader;
import java.util.List;
import java.util.Locale;

@Setter
@Getter
@Builder
public class Visitor {

    private String ip;

    private String referer;

    private String userAgent;

    private String acceptLanguage;

    private boolean crawler;

    private Locale locale;

}
