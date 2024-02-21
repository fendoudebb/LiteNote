package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import z.note.lite.entity.StartupLog;

@Mapper
public interface StartupLogMapper {


    int insert(StartupLog startupLog);

}
