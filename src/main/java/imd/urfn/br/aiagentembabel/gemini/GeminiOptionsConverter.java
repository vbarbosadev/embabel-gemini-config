package imd.urfn.br.aiagentembabel.gemini;

import com.embabel.common.ai.model.LlmOptions;
import com.embabel.common.ai.model.OptionsConverter;
import org.jetbrains.annotations.NotNull;
import org.springframework.ai.openai.OpenAiChatOptions;

public class GeminiOptionsConverter implements OptionsConverter<OpenAiChatOptions> {


    @NotNull
    @Override
    public OpenAiChatOptions convertOptions(LlmOptions options) {

        if (options == null) {
            return OpenAiChatOptions.builder().build();
        }

        var builder = OpenAiChatOptions.builder();


        if (options.getModel() != null) {
            builder.build().setModel(options.getModel());
        }

        if (options.getTemperature() != null) {
            builder.build().setTemperature(options.getTemperature());
        }

        if (options.getMaxTokens() != null) {
            builder.build().setMaxTokens(options.getMaxTokens());
        }

        if (options.getTopP() != null) {
            builder.build().setTopP(options.getTopP());
        }

        if (options.getFrequencyPenalty() != null) {
            builder.build().setFrequencyPenalty(options.getFrequencyPenalty());
        }

        if (options.getPresencePenalty() != null) {
            builder.build().setPresencePenalty(options.getPresencePenalty());
        }

        return builder.build();
    }

}
