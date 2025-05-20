package app.server.openai.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RouteController {

    @GetMapping("/ask_view")
    public String askView() {
        return "ask";
    }
}
