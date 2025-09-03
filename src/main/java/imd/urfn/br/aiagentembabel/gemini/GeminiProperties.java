package imd.urfn.br.aiagentembabel.gemini;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.lang.Nullable;

import java.util.Map;

@ConfigurationProperties(prefix = "gemini")
public record GeminiProperties(
        String defaultBaseUrl,
        Map<String, ModelConfig> models)
{
    public record ModelConfig(
            String name,
            String apiKey,
            @Nullable String baseUrl,
            String path) {}
}