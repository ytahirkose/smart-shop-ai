package com.smartshopai.common.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.ai.vectorstore.inmemory.InMemoryVectorStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

/**
 * Central AI bean configuration shared across micro-services.
 * Provides lightweight, in-memory defaults so that the application context
 * can start even when cloud AI credentials are missing in local/dev setups.
 */
@Configuration
public class AiBeansConfig {

    /**
     * Spring-AI ChatClient builder. In production, Spring Boot auto-config can
     * override this bean with a vendor-specific builder when API keys are present.
     */
    @Bean
    public ChatClient.Builder chatClientBuilder() {
        return ChatClient.builder();
    }

    /**
     * Very simple EmbeddingModel implementation that returns zero vectors.
     * This keeps the codebase compiling and the context loading when a real
     * embedding provider is not configured.
     */
    @Bean
    public EmbeddingModel embeddingModel() {
        return texts -> {
            // Return a single zero vector per input text
            return new EmbeddingResponse(List.of(new float[768]));
        };
    }

    /**
     * In-memory VectorStore backed by the dummy EmbeddingModel above. Replace
     * with Pinecone/Weaviate implementations in production.
     */
    @Bean
    public VectorStore vectorStore(EmbeddingModel embeddingModel) {
        return new InMemoryVectorStore(embeddingModel);
    }
}
