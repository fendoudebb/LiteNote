package z.note.lite.service.api.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.core.JdbcAggregateTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import z.note.lite.dto.response.SysUserDto;
import z.note.lite.model.admin.NameOnly;
import z.note.lite.model.admin.SysUser;
import z.note.lite.repository.api.SysUserRepository;
import z.note.lite.service.api.SysUserService;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class SysUserServiceImpl implements SysUserService {

    private final SysUserRepository sysUserRepository;

    private final JdbcAggregateTemplate jdbcAggregateTemplate;

    @Override
    public Page<SysUserDto> getUsers(Pageable pageable) {
        SysUser probe = new SysUser();
        probe.setUsername("admin");

        Criteria from = Criteria.from(List.of(Criteria.where("username").is("admin")));
        Query query = Query.query(from);
        Query columns = query.columns("id", "username");
        Optional<SysUser> one = jdbcAggregateTemplate.findOne(columns, SysUser.class);
        if (one.isPresent()) {
            SysUser sysUser = one.get();
        }

        Page<SysUser> all = sysUserRepository.findAll(Example.of(probe), pageable);

        Page<NameOnly> allBy = sysUserRepository.getAllBy(pageable);

        Page<NameOnly> allBy1 = sysUserRepository.getAllBy(Example.of(probe), pageable);

        System.out.println(all.getContent());
        return sysUserRepository.findAllBy(pageable);
//        return null;
    }

}
