package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class TopicPostMonthlyStats {

    private String topic;

    private Integer year;

    private Integer month;

    private Integer monthCount;

    private Integer cumulativeCount;

}
