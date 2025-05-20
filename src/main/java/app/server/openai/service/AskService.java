package app.server.openai.service;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatOptions;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AskService {

    private final ChatModel chatModel;

    public String getResponse(String message) {
        return chatModel.call(message);
    }

    public String getResponseWithOptions(String message) {
        return chatModel.call(new Prompt(
                message,
                OpenAiChatOptions.builder()
                        .model("gpt-4o")
                        .temperature(0.8)
                        .build()))
                .getResult()
                .getOutput()
                .getText();
    }
}
