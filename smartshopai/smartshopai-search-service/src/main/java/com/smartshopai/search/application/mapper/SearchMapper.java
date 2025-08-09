package com.smartshopai.search.application.mapper;

import com.smartshopai.search.application.dto.response.SearchResponse;
import com.smartshopai.search.domain.entity.SearchIndex;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper for search entities and DTOs
 */
@Mapper(componentModel = "spring")
public interface SearchMapper {

    @Mapping(target = "id", source = "productId")
    SearchResponse.SearchResult toSearchResult(SearchIndex searchIndex);

    List<SearchResponse.SearchResult> toSearchResultList(List<SearchIndex> searchIndices);
}
