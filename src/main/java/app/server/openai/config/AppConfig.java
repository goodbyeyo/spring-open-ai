package app.server.openai.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
public class AppConfig {

    @Value("classpath:/prompt.txt")
    private Resource resource;

    @Bean
    public CharacterEncodingFilter characterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    // ChatClient -> OpenAI API Key -> LLM(Open AI)
    @Bean
    @Qualifier("chatBasicClient")
    public ChatClient chatBasicClient(ChatClient.Builder chatClientBuilder) {
        // system message : LLM 역할 부여
        // return chatClientBuilder.defaultSystem("당신은 교육 튜터입니다. 개념을 명확하고 간단하게 설명하세요").build();
        // return chatClientBuilder.defaultSystem(resource).build();
        return chatClientBuilder.build();
        //return chatClientBuilder.defaultAdvisors(new MessageChatMemoryAdvisor(MessageWindowChatMemory.builder().build())).build();
    }

    @Bean
    @Qualifier("chatResourceClient")
    public ChatClient chatResourceClient(ChatClient.Builder clientBuilder) {
        return clientBuilder.defaultSystem(resource).build();
    }

    @Bean
    @Qualifier("chatMemoryAdvisorClient")
    public ChatClient chatMemoryAdvisorClient(ChatClient.Builder chatClientBuilder) {
        return chatClientBuilder.defaultAdvisors(new MessageChatMemoryAdvisor(MessageWindowChatMemory.builder().build())).build();
        // @Deprecated : InMemoryChatMemory
        // new MessageChatMemoryAdvisor(new InMemoryChatMemory())).build();
    }
}
