package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.entity.RecordSearch;
import z.note.lite.entity.RecordSearchRank;
import z.note.lite.mapper.portal.RecordSearchMapper;

import java.util.List;

@Service
public class SearchService {

    @Resource
    RecordSearchMapper searchMapper;

    public List<RecordSearchRank> getRankSearches() {
        return searchMapper.getRankSearches();
    }

    public int insert(RecordSearch search) {
        return searchMapper.insert(search);
    }

}
