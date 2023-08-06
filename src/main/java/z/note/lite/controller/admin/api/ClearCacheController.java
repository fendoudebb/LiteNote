package z.note.lite.controller.admin.api;

import jakarta.annotation.Resource;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import z.note.lite.constant.mvc.Endpoint;

@RestController
public class ClearCacheController {

    @Resource
    public MessageSource messageSource;

    @DeleteMapping(Endpoint.Api.CLEAN_CACHE_I18N) // /api/clear/cache/i18n
    public Boolean i18n() {
        if (messageSource instanceof ReloadableResourceBundleMessageSource reloadableMsgSrc) {
            reloadableMsgSrc.clearCacheIncludingAncestors();
            return true;
        }
        return false;
    }


}
