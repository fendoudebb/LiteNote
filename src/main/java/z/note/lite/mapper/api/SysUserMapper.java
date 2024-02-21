package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import z.note.lite.entity.SysUser;

@Mapper
public interface SysUserMapper {

    @Select("select * from sys_user where username = #{username}")
    SysUser findByUsername(String username);

}
