package se.callista.cadec2023.product.domain.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import se.callista.cadec2023.product.domain.entity.ProductVariant;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {

    @JsonProperty("productId")
    private Long productId;

    @NotNull
    @Size(max = 255)
    @JsonProperty("name")
    private String name;

    @NotNull
    @Size(max = 255)
    @JsonProperty("articleId")
    private String articleId;

    @NotNull
    @JsonProperty("inventory")
    private Long inventory;

    public static Product fromEntity(ProductVariant product, long inventory) {
        return Product
            .builder()
            .productId(product.getProductId())
            .name(product.getName())
            .articleId(product.getArticleId())
            .inventory(inventory)
            .build();
    }

    public static ProductVariant fromValue(Product product) {
        return ProductVariant
            .builder()
            .productId(product.getProductId())
            .name(product.getName())
            .articleId(product.getArticleId())
            .build();
    }
}
