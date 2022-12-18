package dev.z.blog.model.admin;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Base {

    @Id
    private Long id;

}
