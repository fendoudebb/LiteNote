package z.note.lite.repository.portal;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import z.note.lite.web.model.common.Search;
import z.note.lite.web.model.common.SearchRank;

import java.util.List;

public interface SearchRepository extends ListCrudRepository<Search, Integer> {

    @Query("select keywords, count(keywords) as count from record_search group by keywords order by count desc limit 5")
    List<SearchRank> getRankSearches();

//    @Modifying
//    @Query("insert into record_search(keywords, took, ip_id, referer, ua_name, ua_os, ua) values(:keywords, :took, :ipId, :referer, :uaNme, :uaOs, :ua)")
//    int insert(@Param("keywords") String keywords, @Param("took") long took, @Param("ipId") long ipId, @Param("referer") String referer, @Param("uaNme") String uaNme, @Param("uaOs") String uaOs, @Param("ua") String ua);

}
