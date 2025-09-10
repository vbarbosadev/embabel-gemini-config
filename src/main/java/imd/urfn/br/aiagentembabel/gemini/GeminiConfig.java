package imd.urfn.br.aiagentembabel.gemini;


import com.embabel.common.ai.model.Llm;
import com.embabel.common.ai.model.OptionsConverter;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.Collections;
import java.util.Objects;

@Configuration
@EnableConfigurationProperties(GeminiProperties.class) 
public class GeminiConfig {



    @Bean
    @Primary
    @Qualifier("geminiChatModel")
    public ChatModel geminiChatModel(GeminiModelProvider provider) {
        return provider.getModel("chat");
    }

    @Bean
    public OptionsConverter<?> geminiOptionsConverter() {
        return new GeminiOptionsConverter();
    }

    @Bean
    public Llm geminiLlm(
            GeminiProperties properties,
            @Qualifier("geminiChatModel") ChatModel chatModel,
            OptionsConverter<?> optionsConverter
    ) {

        String modelName = Objects.requireNonNull(properties.models().get("chat"),
                "Configuração para 'gemini.models.chat' não encontrada."
        ).name();

        return new Llm(
                modelName,
                "Google",
                chatModel,
                optionsConverter,
                null,
                Collections.emptyList(),
                null
        );
    }


    @Bean
    @Qualifier("geminiChatModel2")
    public ChatModel geminiChatModel2(GeminiModelProvider provider) {
        return provider.getModel("chat2");
    }

    @Bean
    public Llm geminiLlm2(
            GeminiProperties properties,
            @Qualifier("geminiChatModel") ChatModel chatModel,
            OptionsConverter<?> optionsConverter
    ) {

        String modelName = Objects.requireNonNull(properties.models().get("chat2"),
                "Configuração para 'gemini.models.chat' não encontrada."
        ).name();

        return new Llm(
                modelName,
                "Google",
                chatModel,
                optionsConverter,
                null,
                Collections.emptyList(),
                null
        );
    }
}
