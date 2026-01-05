package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.OffsetDateTime;

@Setter
@Getter
@ToString
public class TopicPostMonthlyStats {

    private String topic;

    private OffsetDateTime month;

    private Integer monthCount;

    private Integer cumulativeCount;

}
