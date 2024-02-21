package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import z.note.lite.entity.RecordPageView;

@Mapper
public interface RecordPageViewMapper {


    int insert(RecordPageView pageView);

    @Select("select count(*) from record_page_view")
    long count();

}
