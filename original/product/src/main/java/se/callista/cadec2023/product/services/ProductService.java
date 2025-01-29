package se.callista.cadec2023.product.services;

import java.util.List;
import se.callista.cadec2023.product.domain.model.Product;

public interface ProductService {

    List<Product> getProducts();

    Product getProduct(String articleId);

    Product createProduct(Product product);

    Product updateProduct(Product product);

    void deleteProduct(String articleId);

}
