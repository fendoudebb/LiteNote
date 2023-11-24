package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.repository.portal.IpRepository;

@Service
public class IpService {

    @Resource
    private IpRepository ipRepository;

    public int countIp() {
        return ipRepository.countIp();
    }

}
