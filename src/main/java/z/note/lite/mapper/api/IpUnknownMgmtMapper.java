package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import z.note.lite.entity.IpUnknown;

import java.util.List;

@Mapper
public interface IpUnknownMgmtMapper {

    List<IpUnknown> findAll(int size, int offset);

    long count();

}
