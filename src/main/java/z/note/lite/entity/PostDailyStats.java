package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PostDailyStats {

    private Integer year;

    private Integer month;

    private Integer day;

    private Integer count;

}
