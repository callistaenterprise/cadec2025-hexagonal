package se.callista.cadec2025.product.application.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Inventory {

    private String articleId;

    private Long stock;
}
