package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface IpMapper {

    @Select("select count(*) from ip_pool")
    int count();

}
