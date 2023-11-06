package z.note.lite.infra;

import java.util.AbstractMap;
import java.util.Map;

public interface PageUtils {

    static Map.Entry<Integer, Integer> getLimitAndOffset(long count, int size, int page) {
        if (size < 1 || size > 20) size = 20;
        int sumPage = (int) ((count - 1) / size + 1);
        if (page < 1 || page > sumPage) page = 1;
        int offset = (page - 1) * size;
        return new AbstractMap.SimpleEntry<>(size, offset);
    }

}
