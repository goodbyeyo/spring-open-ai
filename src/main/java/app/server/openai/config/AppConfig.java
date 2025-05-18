package app.server.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.InMemoryChatMemory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class AppConfig {

    @Value("classpath:/prompt.txt")
    private Resource resource;

    // ChatClient -> OpenAI API Key -> LLM(Open AI)
    @Bean
    public ChatClient chatClient(ChatClient.Builder chatClientBuilder) {
        // system message : LLM 역할 부여
        // return chatClientBuilder.defaultSystem("당신은 교육 튜터입니다. 개념을 명확하고 간단하게 설명하세요").build();
        // return chatClientBuilder.defaultSystem(resource).build();
        return chatClientBuilder.build();
    }

    @Bean
    public ChatClient chatPromptResource(ChatClient.Builder clientBuilder) {
        return clientBuilder.defaultSystem(resource).build();
    }

    @Bean
    public ChatClient chatMemoryAdvisor(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.defaultAdvisors(
                new MessageChatMemoryAdvisor(new InMemoryChatMemory())).build();
    }
}
