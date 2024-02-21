package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.Ip;
import z.note.lite.entity.IpUnknown;
import z.note.lite.mapper.api.IpMgmtMapper;
import z.note.lite.mapper.api.IpUnknownMgmtMapper;
import z.note.lite.response.PageableRes;

import java.util.List;

@Service
public class IpMgmtService {

    @Resource
    IpMgmtMapper ipMgmtMapper;

    @Resource
    IpUnknownMgmtMapper ipUnknownMgmtMapper;

    public PageableRes findAll(int page, int size, String ip) {
        List<Ip> list = ipMgmtMapper.findAll(size, (page - 1) * size, ip);
        long count = ipMgmtMapper.count(ip);
        PageableRes res = new PageableRes();
        res.setList(list);
        res.setCount(count);
        return res;
    }

    public PageableRes findAllUnknown(int page, int size) {
        List<IpUnknown> list = ipUnknownMgmtMapper.findAll(size, (page - 1) * size);
        long count = ipUnknownMgmtMapper.count();
        PageableRes postsDto = new PageableRes();
        postsDto.setList(list);
        postsDto.setCount(count);
        return postsDto;
    }

}
