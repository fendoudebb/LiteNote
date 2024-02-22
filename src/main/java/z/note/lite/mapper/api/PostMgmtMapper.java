package z.note.lite.mapper.api;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import z.note.lite.config.mybatis.ListTypeHandler;
import z.note.lite.entity.Post;
import z.note.lite.request.PostReq;

import java.time.OffsetDateTime;
import java.util.List;

@Mapper
public interface PostMgmtMapper {

    @Select("select * from post where id = #{id}")
    @Results({
            @Result(column = "topics", property = "topics", typeHandler = ListTypeHandler.class),
            @Result(column = "images", property = "images", typeHandler = ListTypeHandler.class)
    })
    Post findById(Integer id);

    List<Post> findAll(int size, int offset, Integer id, Integer status, OffsetDateTime startTs, OffsetDateTime endTs);

    long count(Integer id, Integer status, OffsetDateTime startTs, OffsetDateTime endTs);

    int create(PostReq post);

    int update(PostReq post);

    int updateStatus(Integer postId, Short status);

    int toggleCommentStatus(Integer postId);
}
