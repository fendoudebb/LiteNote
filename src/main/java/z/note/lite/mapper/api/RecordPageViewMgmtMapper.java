package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import z.note.lite.entity.RecordPageView;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface RecordPageViewMgmtMapper {

    List<RecordPageView> findAll(int size, int offset, Integer status, OffsetDateTime startTs, OffsetDateTime endTs);

    long count(Integer status, OffsetDateTime startTs, OffsetDateTime endTs);

}
