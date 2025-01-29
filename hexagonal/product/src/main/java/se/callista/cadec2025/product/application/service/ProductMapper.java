package se.callista.cadec2025.product.application.service;

import org.springframework.stereotype.Component;
import se.callista.cadec2025.product.application.domain.Product;
import se.callista.cadec2025.product.application.domain.ProductVariant;

@Component
public class ProductMapper {

    public Product fromModel(ProductVariant product, long inventory) {
        return Product.builder()
            .articleId(product.getArticleId())
            .name(product.getName())
            .inventory(inventory)
            .build();
    }

}
