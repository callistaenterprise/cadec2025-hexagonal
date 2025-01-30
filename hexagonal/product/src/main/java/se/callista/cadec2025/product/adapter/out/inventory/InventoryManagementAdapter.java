package se.callista.cadec2025.product.adapter.out.inventory;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.concurrent.Semaphore;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import se.callista.cadec2025.product.application.port.out.inventory.ForManagingInventory;
import se.callista.cadec2025.product.application.domain.Inventory;
import se.callista.cadec2023.inventory.model.InventoryValue;

@Component
public class InventoryManagementAdapter implements ForManagingInventory {

    private final Semaphore semaphore;

    private final RestClient.Builder restClientBuilder;

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    private final InventoryMapper inventoryMapper;

    @Value("${inventory.url}")
    private String url;

    public InventoryManagementAdapter(
        @Value("${inventory.session.max:1000}") int permits,
        RestClient.Builder restClientBuilder,
        JmsTemplate jmsTemplate, ObjectMapper objectMapper,
        InventoryMapper inventoryMapper) {
        semaphore = new Semaphore(permits);
        this.restClientBuilder = restClientBuilder;
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = objectMapper;
        this.inventoryMapper = inventoryMapper;
    }

    @Override
    public void createInventory(String articleId) {
        InventoryValue inventoryValue =
            InventoryValue
                .builder()
                .articleId(articleId)
                .stock(0L)
                .build();
        jmsTemplate.convertAndSend("stock", marshall(inventoryValue));
    }

    @Override
    @SneakyThrows
    public Inventory getInventory(String articleId) {
        try {
            semaphore.acquire();
            InventoryValue inventoryValue = restClientBuilder
                .build()
                .get()
                .uri(url + articleId)
                .retrieve()
                .toEntity(InventoryValue.class)
                .getBody();
            return inventoryValue != null ? inventoryMapper.fromValue(inventoryValue) : null;
        } finally {
            semaphore.release();
        }
    }

    @SneakyThrows
    private String marshall(InventoryValue inventoryValue) {
        return objectMapper.writeValueAsString(inventoryValue);
    }

}
