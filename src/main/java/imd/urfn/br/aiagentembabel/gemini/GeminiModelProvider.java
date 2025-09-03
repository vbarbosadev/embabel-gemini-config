package imd.urfn.br.aiagentembabel.gemini;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestClient;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class GeminiModelProvider {

    private final Map<String, ChatModel> modelCache = new ConcurrentHashMap<>();

    public GeminiModelProvider(GeminiProperties properties) {

        for (Map.Entry<String, GeminiProperties.ModelConfig> entry : properties.models().entrySet()) {
            String modelKey = entry.getKey();
            GeminiProperties.ModelConfig config = entry.getValue();

            String baseUrl = StringUtils.hasText(config.baseUrl())
                    ? config.baseUrl()
                    : properties.defaultBaseUrl();

            if (!StringUtils.hasText(baseUrl)) {
                throw new IllegalStateException("O 'baseUrl' não foi definido para o modelo '" + modelKey + "' nem um 'defaultBaseUrl' foi fornecido.");
            }

            RestClient restClient = createRestClientForModel(baseUrl, config.apiKey());

            ChatModel model = new GeminiCustomLlm(restClient, config.name(), config.path());

            modelCache.put(modelKey, model);
        }
    }


    public ChatModel getModel(String key) {
        ChatModel model = modelCache.get(key);
        if (model == null) {
            throw new IllegalArgumentException("Nenhum modelo Gemini foi configurado com a chave: '" + key + "'");
        }
        return model;
    }

    private RestClient createRestClientForModel(String baseUrl, String apiKey) {
        return RestClient.builder()
                .baseUrl(baseUrl)
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}