package z.note.lite.task;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import z.note.lite.entity.Ip;
import z.note.lite.entity.IpUnknown;
import z.note.lite.response.PageableRes;
import z.note.lite.service.api.IpMgmtService;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class UnknownIpQuery {

    @Resource
    IpMgmtService ipMgmtService;

    @SuppressWarnings("unchecked")
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.MINUTES)
    public void queryUnknownIp() {
        PageableRes pageableRes = ipMgmtService.findAllUnknown(1, 10);
        if (pageableRes.getCount() == 0) {
            return;
        }
        List<IpUnknown> list = (List<IpUnknown>) pageableRes.getList();
        list.forEach(ipUnknown -> {
            Ip ip = ipMgmtService.findByIp(ipUnknown.getIp());
            if (ip.getAddress() != null) {
                ipMgmtService.deleteByIp(ipUnknown.getIp());
            }
        });
    }

}
