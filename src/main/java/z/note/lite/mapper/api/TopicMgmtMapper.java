package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import z.note.lite.entity.Topic;

import java.util.List;

@Mapper
public interface TopicMgmtMapper {

    List<Topic> findAll(@Param("size") int size, @Param("offset") int offset, @Param("topic") String topic);

    long count(String topic);

    @Insert("insert into topic(name, sort) values(#{name}, COALESCE((select max(sort) from topic), 0)+1)")
    int insert(String name);

    @Update("update topic set name = #{name}, sort = #{sort}, update_ts = current_timestamp where id = #{id}")
    int update(Topic topic);

    @Select("select * from topic where name = #{name}")
    Topic findByName(String name);

}
