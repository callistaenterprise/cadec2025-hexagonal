package se.callista.cadec2025.product.application.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Product {

    private String articleId;

    private String name;

    private Long inventory;

}
