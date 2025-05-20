package app.server.openai.controller;

import app.server.openai.service.AskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AskController {

    private final AskService askService;

    @GetMapping("/ask")
    public String ask(@RequestParam String message ) {
        return askService.getResponse(message);
    }

    @GetMapping("/ask-ai")
    public String ask_ai(@RequestParam String message ) {
        return askService.getResponseWithOptions(message);
    }
}
