package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import z.note.lite.entity.Link;

import java.util.List;

@Mapper
public interface LinkMapper {

    @Select("select website, url from link where status=0 order by sort")
    List<Link> list();

}
