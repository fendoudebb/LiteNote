package z.note.lite.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import z.note.lite.controller.Endpoint;

@Controller
public class DataController {

    @GetMapping(Endpoint.Portal.DATA) // /data.html
    public String data() {
        return "portal/data";
    }

}
