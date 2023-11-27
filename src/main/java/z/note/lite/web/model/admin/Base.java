package z.note.lite.web.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Setter
@Getter
public abstract class Base {

    @Id
    private Long id;

    @CreatedBy
    private String createBy;

    @CreatedDate
    private LocalDateTime createDate;

    @LastModifiedBy
    private String updateBy;

    @LastModifiedDate
    private LocalDateTime updateDate;

}
