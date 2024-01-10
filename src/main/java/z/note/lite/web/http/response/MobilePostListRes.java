package z.note.lite.web.http.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Setter
@Getter
@ToString
@Builder
public class MobilePostListRes {

    private int currentPage;

    private int totalPage;

    private List<?> posts;

}
