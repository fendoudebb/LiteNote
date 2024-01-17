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
public class MobileSearchListRes {

    private boolean next;

    private List<?> posts;

}
