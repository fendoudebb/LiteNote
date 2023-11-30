package z.note.lite.repository.portal;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.ListCrudRepository;
import z.note.lite.web.model.common.Ip;

public interface IpRepository extends ListCrudRepository<Ip, Integer> {

    @Query("select count(*) from ip_pool")
    int countIp();

}
