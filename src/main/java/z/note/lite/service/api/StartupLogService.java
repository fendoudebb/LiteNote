package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.repository.api.StartupLogRepository;
import z.note.lite.web.model.admin.StartupLog;

@Service
public class StartupLogService {

    @Resource
    StartupLogRepository startupLogRepository;

    public StartupLog insert(StartupLog startupLog) {
        return startupLogRepository.save(startupLog);
    }

}
