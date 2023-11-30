package z.note.lite.repository.portal;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import z.note.lite.web.model.common.Topic;

import java.util.List;

public interface TopicRepository extends ListCrudRepository<Topic, Integer> {

    @Query("select name from topic order by sort")
    List<Topic> getRecommendTopics();

}
