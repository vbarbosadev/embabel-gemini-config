package imd.urfn.br.aiagentembabel;

import com.embabel.agent.config.annotation.EnableAgentShell;
import com.embabel.agent.config.annotation.EnableAgents;
import com.embabel.agent.config.annotation.LocalModels;
import com.embabel.agent.config.annotation.McpServers;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableAgentShell // possivel  erro ao baixar o pacote embabel atual
@EnableAgents(mcpServers = {
        McpServers.DOCKER_DESKTOP
})
public class AiAgentEmbabelApplication {

    public static void main(String[] args) {
        SpringApplication.run(AiAgentEmbabelApplication.class, args);
    }

}
