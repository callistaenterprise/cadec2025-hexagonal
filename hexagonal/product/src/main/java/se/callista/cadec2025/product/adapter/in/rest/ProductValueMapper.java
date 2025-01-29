package se.callista.cadec2025.product.adapter.in.rest;

import org.springframework.stereotype.Component;
import se.callista.cadec2025.product.adapter.in.rest.model.ProductValue;
import se.callista.cadec2025.product.application.domain.Product;

@Component
public class ProductValueMapper {

    public ProductValue fromModel(Product product) {
        return ProductValue.builder()
            .articleId(product.getArticleId())
            .name(product.getName())
            .inventory(product.getInventory())
            .build();
    }

    public Product fromValue(ProductValue productValue) {
        return Product.builder()
            .articleId(productValue.getArticleId())
            .name(productValue.getName())
            .inventory(productValue.getInventory())
            .build();
    }
}
