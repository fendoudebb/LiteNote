package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import z.note.lite.entity.Ip;

import java.util.List;

@Mapper
public interface IpMgmtMapper {

    @Select("select * from ip where id = #{id}")
    Ip findById(Integer id);

    List<Ip> findAll(int size, int offset, String ip);

    long count(String ip);

}
