package se.callista.cadec2025.product.adapter.out.inventory;

import org.springframework.stereotype.Component;
import se.callista.cadec2025.product.application.domain.Inventory;
import se.callista.cadec2023.inventory.model.InventoryValue;

@Component
public class InventoryMapper {

    public Inventory fromValue(InventoryValue value) {
        return Inventory.builder()
            .articleId(value.getArticleId())
            .stock(value.getStock())
            .build();
    }

    public InventoryValue toValue(Inventory inventory) {
        return InventoryValue.builder()
            .articleId(inventory.getArticleId())
            .stock(inventory.getStock())
            .build();
    }

}
