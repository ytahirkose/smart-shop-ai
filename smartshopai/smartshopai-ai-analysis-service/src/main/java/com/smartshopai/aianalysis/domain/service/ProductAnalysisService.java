package com.smartshopai.aianalysis.domain.service;

import com.smartshopai.aianalysis.domain.entity.ProductAnalysis;
import com.smartshopai.aianalysis.domain.repository.ProductAnalysisRepository;
import com.smartshopai.aianalysis.infrastructure.client.ProductServiceClient;
import com.smartshopai.aianalysis.infrastructure.dto.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductAnalysisService {

    private final ProductAnalysisRepository analysisRepository;
    private final ProductServiceClient productServiceClient;
    private final ChatModel chatModel;
    private final EmbeddingModel embeddingModel;
    private final VectorStore vectorStore;

    public ProductAnalysis analyzeProduct(String productId, Set<String> roles) {
        log.info("Starting AI analysis for product ID: {} with roles: {}", productId, roles);

        Product product = productServiceClient.getProductById(productId);

        String promptString;
        if (roles.contains("ROLE_PREMIUM")) {
            log.info("Generating PREMIUM analysis for user.");
            promptString = """
                    Perform an expert-level analysis for a premium user. The analysis must be detailed, insightful, and comparative.
                    Your analysis should include:
                    1.  **In-Depth Key Features**: Go beyond the obvious. Explain what the technical specifications mean in practice.
                    2.  **Deep Pros and Cons**: Provide a detailed, balanced list of strengths and weaknesses.
                    3.  **Competitor Comparison**: Compare this product with its top 2-3 direct competitors in the market. Highlight key differences in features, performance, and price.
                    4.  **Long-term Viability**: Assess the product's potential longevity. Are there any known issues? Will it be outdated soon?
                    5.  **Final Verdict for a Premium User**: Is this a smart purchase? Justify your final recommendation.
                    
                    Product Details:
                    - Name: {name}
                    - Description: {description}
                    - Price: {price} {currency}
                    - Specifications: {specifications}
                    """;
        } else {
            log.info("Generating STANDARD analysis for user.");
            promptString = """
                    Analyze the following product for a potential buyer. Provide a comprehensive, easy-to-understand summary.
                    Your analysis should include:
                    1.  **Key Features Summary**: A brief, bullet-point summary of the most important technical specifications.
                    2.  **Pros and Cons**: A balanced list of potential advantages and disadvantages.
                    3.  **Target Audience**: Who is this product best suited for? (e.g., students, professionals, gamers).
                    4.  **Value Proposition**: Based on its price and features, is this product a good value? Why?
                    
                    Product Details:
                    - Name: {name}
                    - Description: {description}
                    - Price: {price} {currency}
                    - Specifications: {specifications}
                    """;
        }

        PromptTemplate promptTemplate = new PromptTemplate(promptString);
        Prompt prompt = promptTemplate.create(Map.of(
                "name", Objects.toString(product.getName(), "N/A"),
                "description", Objects.toString(product.getDescription(), "N/A"),
                "price", Objects.toString(product.getCurrentPrice(), "N/A"),
                "currency", Objects.toString(product.getCurrency(), "N/A"),
                "specifications", Objects.toString(product.getSpecifications(), "Not available")
        ));

        String analysisContent = chatModel.call(prompt).getResult().getOutput().getContent();

        String embeddingText = product.getName() + " " + product.getDescription() + " " + analysisContent;
        float[] rawEmbeddings = embeddingModel.embed(embeddingText);
        List<Double> embeddings = new java.util.ArrayList<>(rawEmbeddings.length);
        for (float f : rawEmbeddings) {
            embeddings.add((double) f);
        }

        ProductAnalysis analysis = ProductAnalysis.builder()
                .productId(productId)
                .analysisContent(analysisContent)
                .embeddings(embeddings)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        ProductAnalysis savedAnalysis = analysisRepository.save(analysis);

        // Add the document to the vector store
        Document document = new Document(savedAnalysis.getId(), embeddingText, Map.of("productId", productId));
        vectorStore.add(List.of(document));

        log.info("Successfully analyzed, embedded, and saved analysis for product ID: {}", productId);
        return savedAnalysis;
    }
    
    public List<ProductAnalysis> findSimilarProducts(List<Double> userEmbedding, int topK) {
        // TODO Replace with real vector similarity search when vectorStore implementation is available
        log.warn("Vector similarity search not yet implemented. Returning empty list.");
        return java.util.Collections.emptyList();
    }
}
