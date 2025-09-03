package imd.urfn.br.aiagentembabel.gemini;

import imd.urfn.br.aiagentembabel.gemini.dto.GeminiApiResponse;
import imd.urfn.br.aiagentembabel.gemini.dto.GeminiRequestDTO;
import imd.urfn.br.aiagentembabel.gemini.dto.Message;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.Generation;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.web.client.RestClient;

import java.util.List;

public class GeminiCustomLlm implements ChatModel {


    private final RestClient restClient;
    private final String modelName;
    private final String uriPath;

    public GeminiCustomLlm(RestClient restClient, String modelName, String uriPath) {
        this.restClient = restClient;
        this.modelName = modelName;
        this.uriPath = uriPath;
    }

    @NotNull
    @Override
    public ChatResponse call(Prompt prompt) {

        System.err.println(prompt.toString());

        String userMessageContent = prompt.getUserMessage().getText();

        String llmResponse = callMyCustomLLM(userMessageContent);

        return new ChatResponse(List.of(
                new Generation(new AssistantMessage(llmResponse))));
    }

    private String callMyCustomLLM(String input) {
        var request = new GeminiRequestDTO(
                this.modelName,
                List.of(new Message("user", input))
        );

        //System.err.println("Request: " + input); // para ver a requisição que vai ate a llm (também é possivel pelo embabel

        GeminiApiResponse apiResponse = restClient.post()
                .uri(this.uriPath)
                .body(request)
                .retrieve()
                .body(GeminiApiResponse.class);

        if (apiResponse == null || apiResponse.choices() == null || apiResponse.choices().isEmpty()) {
            throw new IllegalStateException("Resposta da API do Gemini veio vazia ou mal formatada.");
        }

        // System.err.println("response: " + apiResponse.toString()); // para ver a resposta que vem da llm (também é possivel pelo embabel

        return apiResponse.choices().getFirst().message().content();
    }

    public String getModelName() {
        return modelName;
    }


    @Override
    public ChatOptions getDefaultOptions() {
        return ChatOptions.builder().model(this.modelName).build();
    }
}
