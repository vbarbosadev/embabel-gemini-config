# Gemini Configuration

### application.properties

Use: gemini.models.\<beanReference>.\<options>

Example: 
```
gemini.models.chat.name=gemini-2.5-flash  
gemini.models.chat.api-key=${GEMINI_API_KEY}  
gemini.models.chat.path=/v1beta/openai/chat/completions  

embabel.models.defaultLlm=gemini-2.5-flash
```

* chat: is a property used to bean find a model options
* name: is a name used to configure in gemini api and set in embabel available models
* path: to complete a base url set in <gemini.default.base-url>  

&nbsp; obs: you need set api-key for all models you set

* [More about gemini OpenAI compatibility](https://ai.google.dev/gemini-api/docs/openai#rest)

### Create a Bean

Using \<chat> example:

```
properties:  
gemini.models.chat.name=gemini-2.5-flash  
gemini.models.chat.api-key=${GEMINI_API_KEY}  
gemini.models.chat.path=/v1beta/openai/chat/completions  

@Bean
@Qualifier("geminiEmbeddingModel")
public ChatModel geminiEmbeddingModel(GeminiModelProvider provider) {
    return provider.getModel("chat");
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

```
Using another model

```
properties:  
gemini.models.<model>.name=model // select a model
gemini.models.<model>.api-key=${GEMINI_API_KEY} // set gemini api-key
gemini.models.<model>.path=/v1beta/openai/chat/completions // deafult


@Qualifier("geminiChatModel2")
    public ChatModel geminiChatModel2(GeminiModelProvider provider) {
        return provider.getModel("chat2");
    }
    
@Bean
public Llm geminiLlm2(
        GeminiProperties properties,
        @Qualifier("geminiChatModel2") ChatModel chatModel,
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

```
