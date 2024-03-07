package z.note.lite.service.api;

import jakarta.annotation.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import z.note.lite.entity.Ip;
import z.note.lite.entity.IpUnknown;
import z.note.lite.mapper.api.IpMgmtMapper;
import z.note.lite.mapper.api.IpUnknownMgmtMapper;
import z.note.lite.model.TaobaoIp;
import z.note.lite.response.PageableRes;

import java.util.List;
import java.util.Objects;

@Service
public class IpMgmtService {

    @Resource
    IpMgmtMapper ipMgmtMapper;

    @Resource
    IpUnknownMgmtMapper ipUnknownMgmtMapper;

    @Resource
    RestTemplate restTemplate;

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

    @Transactional
    public Ip findByIp(String ipStr) {
        Ip ip = ipMgmtMapper.findByIp(ipStr);
        if (ip != null && StringUtils.hasText(ip.getAddress())) {
            return ip;
        }
        ResponseEntity<TaobaoIp> response = restTemplate.getForEntity("https://ip.taobao.com/getIpInfo.php?ip=" + ipStr, TaobaoIp.class);
        if (response.getStatusCode() != HttpStatus.OK || response.getBody() == null || response.getBody() == null) {
            ipUnknownMgmtMapper.insert(ipStr);
            if (ip == null) {
                ip = new Ip();
                int id = ipMgmtMapper.insertWithIp(ipStr);
                ip.setId(id);
            }
        } else {
            TaobaoIp body = response.getBody();
            TaobaoIp.Data data = body.getData();
            Ip upsertIp = new Ip();
            upsertIp.setIp(ipStr);
            if (!Objects.equals(data.getCountryCn().toLowerCase(), "xx")) {
                upsertIp.setCountry(data.getCountryCn());
                upsertIp.setCountryId(data.getCountryCode());
            }
            if (!Objects.equals(data.getProvinceCn().toLowerCase(), "xx")) {
                upsertIp.setRegion(data.getProvinceCn());
                upsertIp.setRegionId(data.getProvinceCode());
            }
            if (!Objects.equals(data.getCityCn().toLowerCase(), "xx")) {
                upsertIp.setCity(data.getCityCn());
                upsertIp.setCityId(data.getCityCode());
            }
            if (!Objects.equals(data.getIspCn().toLowerCase(), "xx")) {
                upsertIp.setIsp(data.getIspCn());
                upsertIp.setIspId(data.getIspCode());
            }
            if (ip != null) {
                upsertIp.setId(ip.getId());
                ip = ipMgmtMapper.update(upsertIp);
            } else {
                ip = ipMgmtMapper.insert(upsertIp);
            }
        }
        return ip;
    }

    public void deleteByIp(String ip) {
        ipUnknownMgmtMapper.deleteByIp(ip);
    }

    public static void main(String[] args) {
        RestTemplate restTemplate1 = new RestTemplate();
        ResponseEntity<TaobaoIp> response = restTemplate1.getForEntity("https://ip.taobao.com/getIpInfo.php?ip=113.87.160.228", TaobaoIp.class);
        TaobaoIp body = response.getBody();
        System.out.println(body);
    }

}
