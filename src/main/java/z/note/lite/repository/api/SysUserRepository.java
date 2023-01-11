package z.note.lite.repository.api;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import z.note.lite.model.admin.SysUser;

public interface SysUserRepository extends ListCrudRepository<SysUser, Long>, PagingAndSortingRepository<SysUser, Long> {

    SysUser findByUsername(String username);

}
