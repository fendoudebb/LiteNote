package z.note.lite.controller.api;

import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.controller.Endpoint;

@RestController
public class CleanCacheController {

    @Resource
    public MessageSource messageSource;

    @DeleteMapping(Endpoint.Api.CLEAN_CACHE) // /api/cache/i18n
    public Boolean i18n() {
        if (messageSource instanceof ReloadableResourceBundleMessageSource reloadableMsgSrc) {
            reloadableMsgSrc.clearCacheIncludingAncestors();
            return true;
        }
        return false;
    }


}
