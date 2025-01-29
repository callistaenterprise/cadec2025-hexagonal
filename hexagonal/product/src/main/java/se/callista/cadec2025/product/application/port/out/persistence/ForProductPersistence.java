package se.callista.cadec2025.product.application.port.out.persistence;

import org.springframework.transaction.annotation.Transactional;
import se.callista.cadec2025.product.application.domain.ProductVariant;

import java.util.List;

public interface ForProductPersistence {

    @Transactional
    Long createProduct(ProductVariant product);

    @Transactional
    void updateProduct(ProductVariant product) throws NoSuchProductException;

    List<ProductVariant> getProducts();

    ProductVariant getProduct(String articleId) throws NoSuchProductException;

}
