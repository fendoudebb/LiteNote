package z.note.lite.web.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table(name = "sys_role")
public class SysRole extends Base {

    private String name;

}
