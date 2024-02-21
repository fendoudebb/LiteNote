package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.StartupLog;
import z.note.lite.mapper.api.StartupLogMapper;

@Service
public class StartupLogService {

    @Resource
    StartupLogMapper startupLogMapper;

    public int create(StartupLog startupLog) {
        return startupLogMapper.insert(startupLog);
    }

}
