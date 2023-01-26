package z.note.lite.controller.api;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/clear/cache")
public class ClearCacheController {

    public final MessageSource messageSource;

    @DeleteMapping("/i18n")
    public Boolean i18n() {
        if (messageSource instanceof ReloadableResourceBundleMessageSource reloadableMsgSrc) {
            reloadableMsgSrc.clearCacheIncludingAncestors();
            return true;
        }
        return false;
    }


}
