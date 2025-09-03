package imd.urfn.br.aiagentembabel;

import com.embabel.agent.api.annotation.AchievesGoal;
import com.embabel.agent.api.annotation.Action;
import com.embabel.agent.api.annotation.Agent;
import com.embabel.agent.api.common.OperationContext;
import com.embabel.agent.domain.io.UserInput;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.nio.charset.Charset;

@Agent(
        name = "chatLlm",
        description = "just call the llm about a question")
public class ChatRequest {




    @Value("classpath:prompt-templates/prompt-template.txt")
    private Resource promptTemplateFile;

    @Action
    @AchievesGoal(description = "Final result is make here")
    public String talk(UserInput userInput, OperationContext operationContext) throws IOException {

        String prompt = promptTemplateFile
                .getContentAsString(Charset.defaultCharset())
                .formatted(
                        userInput
                );
        return operationContext.ai()
                .withAutoLlm()
                .createObject("Responda a seguinte pergunta do usuario: '%s'".formatted(userInput.getContent()),
                        String.class);
    }

}
