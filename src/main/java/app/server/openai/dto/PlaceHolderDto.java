package app.server.openai.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceHolderDto {
    @JsonProperty("subject")
    private String subject;

    @JsonProperty("tone")
    private String tone;

    @JsonProperty("message")
    private String message;
}
