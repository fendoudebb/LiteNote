package z.note.lite.repository.portal;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import z.note.lite.web.model.common.Link;

import java.util.List;

public interface LinkRepository extends ListCrudRepository<Link, Integer> {

    @Query("select website, url from link where status=0 order by sort")
    List<Link> getLinks();

}
