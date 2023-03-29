package z.note.lite.controller.portal;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import z.note.lite.constant.mvc.Endpoint;

@Controller
public class PortalErrorController implements ErrorController {

    @GetMapping(value = Endpoint.Portal.ERROR, name = "portal.error")
    public String error() {
        return "portal/error";
    }

}
