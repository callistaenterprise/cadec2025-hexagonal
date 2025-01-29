package se.callista.cadec2025.product.application.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import se.callista.cadec2025.product.application.domain.Inventory;
import se.callista.cadec2025.product.application.domain.Product;
import se.callista.cadec2025.product.application.domain.ProductVariant;
import se.callista.cadec2025.product.application.port.in.create.CreateProductCommand;
import se.callista.cadec2025.product.application.port.in.create.ForCreatingProducts;
import se.callista.cadec2025.product.application.port.in.manage.ForManagingProducts;
import se.callista.cadec2025.product.application.port.in.manage.GetProductCommand;
import se.callista.cadec2025.product.application.port.in.manage.UpdateProductCommand;
import se.callista.cadec2025.product.application.port.out.inventory.ForManagingInventory;
import se.callista.cadec2025.product.application.port.out.persistence.ForProductPersistence;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ProductService implements ForCreatingProducts, ForManagingProducts {

    private final ForManagingInventory inventoryPort;
    private final ForProductPersistence persistencePort;
    private final ProductMapper mapper;

    @Override
    @Transactional
    public void createProduct(CreateProductCommand command) {
        ProductVariant product = ProductVariant.builder()
            .name(command.getName())
            .articleId(command.getArticleId())
            .build();
        persistencePort.createProduct(product);
        inventoryPort.createInventory(command.getArticleId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProducts() {
        return persistencePort.getProducts().stream()
            .map(product -> {
                long stock;
                try {
                    Inventory inventory = inventoryPort.getInventory(product.getArticleId());
                    stock = inventory.getStock();
                } catch (RuntimeException e) {
                    stock = 0;
                }
                return mapper.fromModel(product, stock);
            })
            .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public Product getProduct(GetProductCommand command) {
        ProductVariant product = persistencePort.getProduct(command.getArticleId());
        long stock;
        try {
            Inventory inventory = inventoryPort.getInventory(product.getArticleId());
            stock = inventory.getStock();
        } catch (RuntimeException e) {
            stock = 0;
        }
        return mapper.fromModel(product, stock);
    }

    @Override
    @Transactional
    public void updateProduct(UpdateProductCommand command) {
        ProductVariant product = persistencePort.getProduct(command.getArticleId());
        product.setName(command.getName());
        persistencePort.updateProduct(product);
    }

}
