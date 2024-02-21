package z.note.lite.mapper.portal;

import org.apache.ibatis.annotations.Mapper;
import z.note.lite.entity.RecordInvalidRequest;

@Mapper
public interface InvalidRequestMapper {


    int insert(RecordInvalidRequest invalidRequest);

}
