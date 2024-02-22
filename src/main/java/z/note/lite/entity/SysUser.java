package z.note.lite.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@Setter
@Getter
@ToString
public class SysUser extends Base {

    private String username;

    private String password;

    private Boolean accountExpired;

    private Boolean accountLocked;

    private Boolean credentialsExpired;

    private Boolean disabled;

    private Set<SysPermission> permissions;

}
