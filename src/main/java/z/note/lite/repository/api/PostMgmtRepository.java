package z.note.lite.repository.api;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import z.note.lite.web.model.common.Post;

public interface PostMgmtRepository extends ListCrudRepository<Post, Integer> {

    @Modifying
    @Query("update post set status=:status, update_ts=current_timestamp where id=:postId")
    int updateStatus(@Param("postId") Integer postId, @Param("status") Short status);

    @Modifying
    @Query("update post set comment_status=1-comment_status, update_ts=current_timestamp where id=:postId")
    int toggleCommentStatus(@Param("postId") Integer postId);

}
