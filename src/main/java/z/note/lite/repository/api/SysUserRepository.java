package z.note.lite.repository.api;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import z.note.lite.dto.response.SysUserDto;
import z.note.lite.model.admin.NameOnly;
import z.note.lite.model.admin.SysUser;

public interface SysUserRepository extends ListCrudRepository<SysUser, Long>, PagingAndSortingRepository<SysUser, Long>, QueryByExampleExecutor<SysUser> {

    SysUser findByUsername(String username);

    Page<SysUserDto> findAllBy(Pageable pageable);

    Page<NameOnly> getAllBy(Pageable pageable);

    Page<NameOnly> getAllBy(Example<SysUser> probe, Pageable pageable);

}
