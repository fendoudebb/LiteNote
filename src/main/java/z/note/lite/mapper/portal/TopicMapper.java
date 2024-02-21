package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import z.note.lite.entity.Topic;

import java.util.List;

@Mapper
public interface TopicMapper {

    @Select("select name from topic order by sort")
    List<Topic> getRecommendTopics();

}
