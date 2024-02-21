package z.note.lite.controller.portal;

import jakarta.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import z.note.lite.config.preferences.Portal;
import z.note.lite.controller.Endpoint;

@Controller
public class MessagesController {

    @Resource
    private Portal portal;

    @GetMapping(Endpoint.Portal.MESSAGES) // /messages.html
    public String messages() {
        return portal.getComment().isEnabled() ? "portal/messages" : "error";
    }

}
