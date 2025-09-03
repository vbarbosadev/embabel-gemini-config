# Configuração Dinâmica de Modelos Gemini para Spring Boot & Embabel

This repository provides an implementation for dynamically configuring Google Gemini AI models in a Spring + Embabel application. The approach uses LLM bean injection from a properties file, enabling a flexible and maintainable architecture, with a focus on integration with the Embabel framework.

## ✨ Advantages
* Total Flexibility: Add, remove, or modify AI models (chat, embedding, etc.) simply by changing the application.properties file, without touching the Java code.
* Centralized Configuration: All API keys, model names, and endpoints are in one place.
* Clean Code: Bean creation logic is isolated, keeping configuration classes (@Configuration) clean and declarative.

## Gemini Configuration

### application.properties

Structure: gemini.models.\<identifier>.\<option>
```
gemini.models // default prefix for all model configurations.
<identifier> (e.g., chat, embedding): This is the unique reference name for your bean. You will use this key to retrieve the specific configuration in your Java code.
<option> (e.g., name, api-key, path): The specific configurations for the model.

options:
name: The official model name to use in the Gemini API call (e.g., gemini-1.5-flash).
api-key: Your Google AI Studio API key. It is mandatory to define a key for each model.
path: The path to the API endpoint that will complement the base URL.
```

Example: 
```
gemini.models.chat.name=gemini-2.5-flash  
gemini.models.chat.api-key=${GEMINI_API_KEY}  
gemini.models.chat.path=/v1beta/openai/chat/completions  

embabel.models.defaultLlm=gemini-2.5-flash
```

* chat: is a property used to bean configuration find a model options
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
