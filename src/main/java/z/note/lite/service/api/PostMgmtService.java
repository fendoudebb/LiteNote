package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import z.note.lite.config.security.authentication.token.SysUserAuthenticationToken;
import z.note.lite.entity.Post;
import z.note.lite.mapper.api.PostMgmtMapper;
import z.note.lite.entity.SysUser;
import z.note.lite.request.PostReq;
import z.note.lite.response.PageableRes;

import java.time.OffsetDateTime;
import java.util.List;

@Service
public class PostMgmtService {

    @Resource
    PostMgmtMapper postMgmtMapper;

    public Post findById(Integer postId) {
        return postMgmtMapper.findById(postId);
    }

    public int create(PostReq req) {
        SysUserAuthenticationToken authentication = (SysUserAuthenticationToken) SecurityContextHolder.getContext().getAuthentication();
        req.setUid(authentication.getSysUser().getId());
        return postMgmtMapper.create(req);
    }

    public int update(PostReq req) {
        return postMgmtMapper.update(req);
    }

    public PageableRes findAll(int page, int size, Integer id, Integer status, OffsetDateTime startTs, OffsetDateTime endTs) {
        int offset = (page - 1) * size;
        List<Post> posts = postMgmtMapper.findAll(size, offset, id, status, startTs, endTs);
        long count = postMgmtMapper.count(id, status, startTs, endTs);
        PageableRes res = new PageableRes();
        res.setList(posts);
        res.setCount(count);
        return res;
    }

    public int updateStatus(Integer postId, Short status) {
        return postMgmtMapper.updateStatus(postId, status);
    }

    public int toggleCommentStatus(Integer postId) {
        return postMgmtMapper.toggleCommentStatus(postId);
    }

}
