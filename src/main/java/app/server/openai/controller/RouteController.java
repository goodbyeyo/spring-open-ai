package app.server.openai.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouteController {

    @GetMapping("/ask_view")
    public String askView() {
        return "ask";
    }

    @GetMapping("/image_view")
    public String imageView() {
        return "image";
    }
}
