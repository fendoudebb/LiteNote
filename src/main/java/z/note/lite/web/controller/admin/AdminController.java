package z.note.lite.web.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import z.note.lite.web.Endpoint;

@Controller
public class AdminController {

    @GetMapping(Endpoint.Admin.LOGIN) // /admin/login
    public String login() {
        return "admin/login";
    }

    @GetMapping(Endpoint.Admin.CONTEXT) // /admin
    public String index() {
        return "admin/index";
    }

}
