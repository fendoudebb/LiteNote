package z.note.lite.repository.api;

import org.springframework.data.repository.ListCrudRepository;
import z.note.lite.web.model.admin.StartupLog;

public interface StartupLogRepository extends ListCrudRepository<StartupLog, Long> {

}
