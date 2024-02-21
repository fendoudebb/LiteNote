package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import z.note.lite.entity.Link;
import z.note.lite.mapper.api.LinkMgmtMapper;
import z.note.lite.request.LinkCreateReq;
import z.note.lite.request.LinkModifyReq;
import z.note.lite.response.PageableRes;

import java.util.List;

@Service
public class LinkMgmtService {

    @Resource
    LinkMgmtMapper linkMgmtMapper;

    public PageableRes findAll(int page, int size) {
        List<Link> list = linkMgmtMapper.findAll(size, (page - 1) * size);
        long count = linkMgmtMapper.count();
        PageableRes res = new PageableRes();
        res.setList(list);
        res.setCount(count);
        return res;
    }

    public int create(LinkCreateReq req) {
        Link link = linkMgmtMapper.findByUrl(req.getUrl());
        if (link != null) throw new IllegalArgumentException("Link Exists");
        link = new Link();
        BeanUtils.copyProperties(req, link);
        return linkMgmtMapper.insert(link);
    }

    public int update(LinkModifyReq req) {
        Link link = linkMgmtMapper.findByUrl(req.getUrl());
        if (link !=null && !link.getId().equals(req.getId())) throw new IllegalArgumentException("Link Exists");
        link = new Link();
        BeanUtils.copyProperties(req, link);
        return linkMgmtMapper.update(link);
    }

    public int updateStatus(Integer id) {
        return linkMgmtMapper.updateStatus(id);
    }

}
