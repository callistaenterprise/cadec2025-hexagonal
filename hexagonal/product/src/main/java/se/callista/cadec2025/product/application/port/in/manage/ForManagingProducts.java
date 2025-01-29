package se.callista.cadec2025.product.application.port.in.manage;

import se.callista.cadec2025.product.application.domain.Product;

import java.util.List;

public interface ForManagingProducts {

    List<Product> getProducts();

    Product getProduct(GetProductCommand command);

    void updateProduct(UpdateProductCommand command);

}
