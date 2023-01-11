package z.note.lite.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.relational.core.mapping.Table;

@Setter
@Getter
@Table(name = "sys_permission")
public class SysPermission extends Base {

    private String name;

    private String value;

}
