package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
//@Table(name = "sys_permission")
public class SysPermission extends Base {

    private String name;

    private String value;

}
