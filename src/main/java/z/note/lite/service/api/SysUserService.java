package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import z.note.lite.infra.PageUtils;
import z.note.lite.web.http.request.SysUserCreateReq;
import z.note.lite.web.http.request.SysUserModifyReq;
import z.note.lite.web.http.response.PageableRes;
import z.note.lite.web.model.admin.SysUser;

import java.util.List;
import java.util.Map;

@Service
public class SysUserService {

    @Resource
    JdbcClient jdbcClient;

    public PageableRes findAll(int page, int size) {
        long count = jdbcClient.sql("select count(*) from sys_user")
                .query(Long.class)
                .single();
        Map.Entry<Integer, Integer> entry = PageUtils.getLimitAndOffset(count, size, page);
        List<SysUser> users = jdbcClient.sql("select * from sys_user order by id limit :limit offset :offset")
                .param("limit", entry.getKey())
                .param("offset", entry.getValue())
                .query(SysUser.class)
                .list();
        PageableRes res = new PageableRes();
        res.setList(users);
        res.setCount(count);
        return res;
    }

    public int create(SysUserCreateReq req) {
        Integer count = jdbcClient.sql("select count(*) from sys_user where username=:username")
                .param("username", req.getUsername())
                .query(Integer.class)
                .single();
        if (count > 0) throw new IllegalArgumentException("User Exists");
        return jdbcClient.sql("insert into sys_user(id, username, password, create_by) values((select max(id) from post) + 1, :username, :password, :createBy)")
                .paramSource(req)
                .update();
    }

    public int update(SysUserModifyReq req) {
        Long id = jdbcClient.sql("select id from sys_user where id=?")
                .param(req.getId())
                .query(Long.class)
                .single();
        if (!req.getId().equals(id)) throw new IllegalArgumentException("User Not Exists");
        return jdbcClient.sql("update sys_user set password=:password, update_by=:updateBy, update_ts=current_timestamp where id=:id")
                .paramSource(req)
                .update();
    }

    public int updateStatus(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return jdbcClient.sql("update sys_user set disabled=not disabled, update_by=:updateBy, update_ts=current_timestamp where id=:id")
                .param("updateBy", authentication.getName())
                .param("id", id)
                .update();
    }

}
