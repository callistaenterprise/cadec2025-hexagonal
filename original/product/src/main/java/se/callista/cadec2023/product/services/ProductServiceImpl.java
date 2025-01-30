package se.callista.cadec2023.product.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import se.callista.cadec2023.product.domain.model.Product;
import se.callista.cadec2023.product.domain.entity.ProductVariant;
import se.callista.cadec2023.product.repository.ProductVariantRepository;
import se.callista.cadec2023.inventory.model.InventoryValue;

@RequiredArgsConstructor
@Component
public class ProductServiceImpl implements ProductService {

    private final ProductVariantRepository productVariantRepository;
    private final InventoryService inventoryService;
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Override
    public Product createProduct(Product product) {
        ProductVariant productVariant = Product.fromValue(product);
        try {
            productVariantRepository.save(productVariant);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateKeyException(product.getArticleId() + " already exists");
        }
        InventoryValue inventoryValue =
            InventoryValue
                .builder()
                .articleId(product.getArticleId())
                .stock(0L)
                .build();
        jmsTemplate.convertAndSend("stock", marshall(inventoryValue));
        return Product.fromEntity(productVariant, 0L);
    }

    @Override
    public List<Product> getProducts() {
        return productVariantRepository.findAllByOrderByProductId().stream()
            .map(product -> {
                long stock;
                try {
                    InventoryValue inventoryValue = inventoryService.getInventory(product.getArticleId());
                    stock = inventoryValue.getStock();
                } catch (RuntimeException e) {
                    stock = 0;
                }
                return Product.fromEntity(product, stock);
            })
            .toList();
    }

    @Override
    public Product getProduct(String articleId) {
        ProductVariant product = productVariantRepository.findByarticleId(articleId)
                .orElseThrow(() -> new EntityNotFoundException("Product " + articleId + " not found"));
        long stock;
        try {
            InventoryValue inventoryValue = inventoryService.getInventory(product.getArticleId());
            stock = inventoryValue.getStock();
        } catch (RuntimeException e) {
            stock = 0;
        }
        return Product.fromEntity(product, stock);
    }

    @Override
    public Product updateProduct(Product updatedProduct) {
        ProductVariant product = productVariantRepository.findByarticleId(updatedProduct.getArticleId())
                .orElseThrow(() -> new EntityNotFoundException("Product " + updatedProduct.getArticleId() + " not found"));
        product.setName(updatedProduct.getName());
        productVariantRepository.save(product);
        long stock;
        try {
            InventoryValue inventoryValue = inventoryService.getInventory(product.getArticleId());
            stock = inventoryValue.getStock();
        } catch (RuntimeException e) {
            stock = 0;
        }
        return Product.fromEntity(product, stock);
    }

    @Override
    public void deleteProduct(final String articleId) {
        ProductVariant product = productVariantRepository.findByarticleId(articleId)
            .orElseThrow(() -> new EntityNotFoundException("Product " + articleId + " not found"));
        productVariantRepository.delete(product);
        InventoryValue inventoryValue =
            InventoryValue
                .builder()
                .articleId(product.getArticleId())
                .stock(-1L)
                .build();
        jmsTemplate.convertAndSend("stock", marshall(inventoryValue));
    }

    @SneakyThrows
    private String marshall(InventoryValue inventoryValue) {
        return objectMapper.writeValueAsString(inventoryValue);
    }

}
