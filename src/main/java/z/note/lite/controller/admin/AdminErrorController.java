package z.note.lite.controller.admin;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import z.note.lite.constant.mvc.Endpoint;

@Controller
public class AdminErrorController implements ErrorController {

    @GetMapping(value = Endpoint.Admin.ERROR)
    public String error() {
        return "admin/error";
    }

}
