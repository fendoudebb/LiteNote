package z.note.lite.model.admin;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Setter
@Getter
@Table(name = "sys_user")
public class SysUser extends Base {

    private String username;

    private String password;

    private Boolean accountExpired;

    private Boolean accountLocked;

    private Boolean credentialsExpired;

    private Boolean disabled;

    @MappedCollection
    @Transient
    private Set<SysPermission> permissions;

}
