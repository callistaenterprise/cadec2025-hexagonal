package se.callista.cadec2023.product.services;

import se.callista.workshop.karate.inventory.model.InventoryValue;

public interface InventoryService {

    InventoryValue getInventory(String articleId);
}
