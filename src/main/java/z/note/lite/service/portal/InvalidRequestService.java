package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.RecordInvalidRequest;
import z.note.lite.mapper.portal.InvalidRequestMapper;

@Service
public class InvalidRequestService {

    @Resource
    InvalidRequestMapper invalidRequestMapper;

    public int insert(RecordInvalidRequest invalidRequest) {
        return invalidRequestMapper.insert(invalidRequest);
    }

}

