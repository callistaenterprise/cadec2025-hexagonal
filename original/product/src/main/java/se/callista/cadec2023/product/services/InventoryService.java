package se.callista.cadec2023.product.services;

import se.callista.cadec2023.inventory.model.InventoryValue;

public interface InventoryService {

    InventoryValue getInventory(String articleId);
}
