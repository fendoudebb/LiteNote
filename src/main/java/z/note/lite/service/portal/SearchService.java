package z.note.lite.service.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import z.note.lite.web.model.common.Search;
import z.note.lite.web.model.common.SearchRank;
import z.note.lite.repository.portal.SearchRepository;

import java.util.List;

@Service
public class SearchService {

    @Resource
    private SearchRepository searchRepository;

    public List<SearchRank> getRankSearches() {
        return searchRepository.getRankSearches();
    }

    public Search insert(Search search) {
        return searchRepository.save(search);
    }

}
