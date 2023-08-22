package z.note.lite.config.preferences;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import z.note.lite.config.preferences.portal.Analysis;
import z.note.lite.config.preferences.portal.Comment;
import z.note.lite.config.preferences.portal.Css;
import z.note.lite.config.preferences.portal.ICP;
import z.note.lite.config.preferences.portal.Meta;
import z.note.lite.config.preferences.portal.Promotion;

import java.util.List;
import java.util.Map;

@Setter
@Getter
@ToString
@Component // 为了在 Thymeleaf 中获取 Bean 对象时名称可以短一些
@ConfigurationProperties(prefix = "preferences.portal")
public class Portal {

    private String name;

    private String author;

    private String keywords;

    private String description;

    private String github;

    private Map<String, List<String>> js;

    private Promotion promotion;

    private Comment comment;

    private ICP icp;

    private List<Meta> meta;

    private List<Css> css;

    private List<Analysis> analysis;

}
