package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.mapper.portal.IpMapper;

@Service
public class IpService {

    @Resource
    IpMapper ipMapper;

    public int count() {
        return ipMapper.count();
    }

}
