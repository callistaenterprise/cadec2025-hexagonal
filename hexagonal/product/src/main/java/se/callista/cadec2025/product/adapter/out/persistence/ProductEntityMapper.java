package se.callista.cadec2025.product.adapter.out.persistence;

import org.springframework.stereotype.Component;
import se.callista.cadec2025.product.application.domain.ProductVariant;

@Component
public class ProductEntityMapper {

    public ProductVariant fromEntity(ProductEntity entity) {
        return ProductVariant.builder()
            .productId(entity.getProductId())
            .name(entity.getName())
            .articleId(entity.getArticleId())
            .build();
    }

    public ProductEntity toEntity(ProductVariant product) {
        return ProductEntity.builder()
            .productId(product.getProductId())
            .name(product.getName())
            .articleId(product.getArticleId())
            .build();
    }

    public ProductEntity updateWith(ProductEntity entity, ProductVariant product) {
        entity.setName(product.getName());
        entity.setArticleId(product.getArticleId());
        return entity;
    }

}
