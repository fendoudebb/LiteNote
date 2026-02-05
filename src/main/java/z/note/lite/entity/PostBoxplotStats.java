package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Setter
@Getter
@ToString
public class PostBoxplotStats {

    private Integer year;
    private BigDecimal min;
    private BigDecimal q1;
    private BigDecimal q2;
    private BigDecimal q3;
    private BigDecimal max;
    private BigDecimal whiskerMin;
    private BigDecimal whiskerMax;

}
