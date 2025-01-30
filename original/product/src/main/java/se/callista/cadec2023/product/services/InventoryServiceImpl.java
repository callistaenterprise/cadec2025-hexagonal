package se.callista.cadec2023.product.services;

import java.util.concurrent.Semaphore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.callista.cadec2023.inventory.model.InventoryValue;

@Component
public class InventoryServiceImpl implements InventoryService {

    private final Semaphore INVENTORY_SEMAPHORE;

    private final RestClient.Builder restClientBuilder;

    @Value("${inventory.url}")
    private String url;

    public InventoryServiceImpl(
        @Value("${inventory.session.max:1000}") int permits, RestClient.Builder restClientBuilder) {
        INVENTORY_SEMAPHORE = new Semaphore(permits);
        this.restClientBuilder = restClientBuilder;
    }

    @Override
    public InventoryValue getInventory(String articleId) {
        try {
            INVENTORY_SEMAPHORE.acquire();
            return restClientBuilder
                .build()
                .get()
                .uri(url + articleId)
                .retrieve()
                .toEntity(InventoryValue.class)
                .getBody();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            INVENTORY_SEMAPHORE.release();
        }
    }
}
