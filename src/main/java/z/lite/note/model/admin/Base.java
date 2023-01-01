package z.lite.note.model.admin;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class Base {

    @Id
    private Long id;

}
