package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import z.note.lite.entity.RecordSearch;
import z.note.lite.entity.RecordSearchRank;

import java.util.List;

@Mapper
public interface RecordSearchMapper {

    int insert(RecordSearch search);

    @Select("select keywords, count(keywords) as count from record_search group by keywords order by count desc limit 5")
    List<RecordSearchRank> getRankSearches();

}
