package app.server.openai.controller;

import app.server.openai.dto.PlaceHolderDto;
import app.server.openai.entity.Answer;
import app.server.openai.entity.Movie;
import app.server.openai.service.ChatService;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    // LLM(gpt-4o) 통신 할 수 있는 객체 : ChatClient
    @GetMapping("/simpleQuestion")
    public String simpleQuestion(@RequestParam("message") String message) {
        return chatService.simpleQuestion(message);
    }

    @GetMapping("/paramQuestion")
    public String paramQuestion(@ModelAttribute PlaceHolderDto dto) {
        return chatService.paramQuestion(dto);
    }

    @GetMapping("/jsonResponse")
    public ChatResponse jsonResponse(String message) {
        return chatService.jsonResponse(message);
    }

    @GetMapping("/objectResponse")
    public Answer objectResponse(String message) {
        return chatService.objectResponse(message);
    }

    @GetMapping("/recipe")
    public Answer recipe(String foodName, String question) {
        return chatService.recipe(foodName, question);
    }

    @GetMapping("/listResponse")
    public List<String> listResponse(String message) {
        return chatService.listResponse(message);
    }

    @GetMapping("/mapResponse")
    public Map<String, String> mapResponse(String message) {
        return chatService.mapResponse(message);
    }

    @GetMapping("/movie")
    public List<Movie> movie(String director) {
        return chatService.movie(director);
    }
}
