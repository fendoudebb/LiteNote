package dev.z.blog.model.admin;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@Table(name = "sys_permission")
public class SysPermission extends Base {

    private String name;

    private String value;

}
