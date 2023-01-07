package z.note.lite.model.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "sys_role")
public class SysRole extends Base {

    private String name;

}
