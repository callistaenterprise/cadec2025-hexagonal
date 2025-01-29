package se.callista.cadec2025.product.application.port.out.inventory;

import org.springframework.transaction.annotation.Transactional;
import se.callista.cadec2025.product.application.domain.Inventory;

public interface ForManagingInventory {

    @Transactional
    void createInventory(String articleId);

    Inventory getInventory(String articleId);
}
