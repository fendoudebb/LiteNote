package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.repository.portal.InvalidRequestRepository;
import z.note.lite.web.model.common.InvalidRequest;

@Service
public class InvalidRequestService {

    @Resource
    private InvalidRequestRepository invalidRequestRepository;

    public InvalidRequest save(InvalidRequest invalidRequest) {
        return invalidRequestRepository.save(invalidRequest);
    }

}

