package imd.urfn.br.aiagentembabel.gemini.dto;


import java.util.List;


public record GeminiRequestDTO(
        String model,
        List<Message> messages
) {}
