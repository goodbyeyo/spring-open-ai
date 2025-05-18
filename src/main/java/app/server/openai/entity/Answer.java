package app.server.openai.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class Answer {
    private String answer;

    public Answer(String answer) {
        this.answer = answer;
    }
}
