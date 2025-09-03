package imd.urfn.br.aiagentembabel.gemini.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record GeminiApiResponse(List<Choice> choices) {
}
