package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import z.note.lite.entity.IpUnknown;
import z.note.lite.entity.Link;

import java.util.List;

@Mapper
public interface LinkMgmtMapper {

    List<Link> findAll(int size, int offset);

    long count();

    @Select("select * from link where url = #{url}")
    Link findByUrl(String url);

    @Insert("insert into link(website, url, webmaster, sort) values(#{website}, #{url}, #{webmaster}, COALESCE((select max(sort) from link), 0)+1)")
    int insert(Link link);

    @Update("update link set website = #{website}, url = #{url}, webmaster = #{webmaster}, sort = #{sort}, update_ts = current_timestamp where id = #{id}")
    int update(Link link);

    @Update("update link set status=1-status, update_ts=current_timestamp where id=#{id}")
    int updateStatus(Integer id);

}
