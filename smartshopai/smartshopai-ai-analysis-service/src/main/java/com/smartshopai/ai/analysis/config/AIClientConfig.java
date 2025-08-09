package com.smartshopai.ai.analysis.config;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.beans.factory.annotation.Qualifier;

@Configuration
public class AIClientConfig {

    @Bean
    @Primary
    public ChatModel primaryChatModel(@Qualifier("openAiChatModel") ChatModel openAiChatModel) {
        return openAiChatModel;
    }

    @Bean
    public org.springframework.ai.vectorstore.VectorStore vectorStore(org.springframework.ai.embedding.EmbeddingModel embeddingModel) {
        // Basit bir in-memory vector store örneği (gelişmiş için Pinecone/Weaviate entegre edilebilir)
        return new org.springframework.ai.vectorstore.SimpleVectorStore(embeddingModel);
    }

    @Bean
    public org.springframework.ai.chat.client.ChatClient.Builder chatClientBuilder(@Qualifier("openAiChatModel") ChatModel chatModel) {
        return org.springframework.ai.chat.client.ChatClient.builder(chatModel);
    }
}


