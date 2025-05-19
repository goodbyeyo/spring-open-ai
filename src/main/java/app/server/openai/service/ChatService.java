package app.server.openai.service;

import app.server.openai.dto.PlaceHolderDto;
import app.server.openai.entity.Answer;
import app.server.openai.entity.Movie;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.converter.ListOutputConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.convert.support.DefaultConversionService;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

@Service
public class ChatService {

    @Value("classpath:/foodTemplate.txt")
    private Resource foodTemplate;

    @Value("classpath:/movieTemplate.txt")
    private Resource movieTemplate;

    private final ChatClient chatClient;

    private final ChatClient chatResourceClient;

    private final ChatClient chatMemoryAdvisorClient;

    private static final PrintStream utf8Out = new PrintStream(System.out, true, StandardCharsets.UTF_8);

    public ChatService(@Qualifier("chatBasicClient") ChatClient chatClient,
                       @Qualifier("chatResourceClient") ChatClient chatResourceClient,
                       @Qualifier("chatMemoryAdvisorClient") ChatClient chatMemoryAdvisorClient) {
        this.chatClient = chatClient;
        this.chatResourceClient = chatResourceClient;
        this.chatMemoryAdvisorClient = chatMemoryAdvisorClient;
    }

    public String simpleQuestion(String message) {
        return Objects.requireNonNull(chatClient.prompt()
                        .user(message)
                        .call()
                        //.content(); // 요청정보를 받는 부분 (문자열)
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }

    public String paramQuestion(PlaceHolderDto dto) {
        return Objects.requireNonNull(chatClient.prompt()
                        .user(dto.getMessage())
                        .system(spec -> spec.param("subject", dto.getSubject())
                                .param("tone", dto.getTone()))
                        .call()
                        .chatResponse())
                .getResult()
                .getOutput()
                .getText();
    }

    public ChatResponse jsonResponse(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .chatResponse();    // chatResponse -> Json
    }

    public Answer objectResponse(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(Answer.class);
    }

    public Answer recipe(String foodName, String question) {
        return chatResourceClient.prompt()
                .user(userSpec -> userSpec.text(foodTemplate)
                        .param("foodName", foodName)
                        .param("question", question))
                .call()
                .entity(Answer.class);
    }

    public List<String> listResponse(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(new ListOutputConverter(new DefaultConversionService()));
    }

    public Map<String, String> mapResponse(String message) {
        return chatClient.prompt()
                .user(message)
                .call()
                .entity(new ParameterizedTypeReference<Map<String, String>>() {});
    }

    public List<Movie> movie(String director) {
        return chatResourceClient.prompt()
                .user(userSpec -> userSpec.text(movieTemplate)
                        .param("director", director)
                        .param("format", "json"))
                .call()
                .entity(new ParameterizedTypeReference<List<Movie>>() {});
    }

    public String getResponse(String message) {
        return chatMemoryAdvisorClient.prompt()
                .user(message)
                .call()
                .content();
    }

    public void startChat() {
        Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);
        utf8Out.println("Enter your message:");
        while (scanner.hasNextLine()) {
            String message = scanner.nextLine();
            if ("exit".equals(message)) {
                utf8Out.println("Goodbye!");
                break;
            }
            String response = getResponse(message);
            utf8Out.println("ChatGPT: " + response);
        }
    }
}
