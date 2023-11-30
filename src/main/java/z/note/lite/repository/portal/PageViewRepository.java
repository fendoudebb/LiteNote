package z.note.lite.repository.portal;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import z.note.lite.web.model.common.PageView;

public interface PageViewRepository extends ListCrudRepository<PageView, Long> {

    @Query("select count(*) from record_page_view")
    long countPageView();

}
