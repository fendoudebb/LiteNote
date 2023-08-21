package z.note.lite.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import z.note.lite.constant.mvc.Endpoint;

@Controller
public class GlobalErrorController implements ErrorController {

    @GetMapping(value = Endpoint.ERROR, name = "error")
    public String error() {
        return "error";
    }

}