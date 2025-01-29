package se.callista.cadec2025.product.application.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProductVariant {

    protected Long productId;

    private String articleId;

    private String name;

}
