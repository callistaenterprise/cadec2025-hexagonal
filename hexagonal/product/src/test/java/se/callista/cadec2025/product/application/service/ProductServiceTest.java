package se.callista.cadec2025.product.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import se.callista.cadec2025.product.application.domain.Product;
import se.callista.cadec2025.product.application.domain.ProductVariant;
import se.callista.cadec2025.product.application.port.in.create.CreateProductCommand;
import se.callista.cadec2025.product.application.port.in.manage.GetProductCommand;
import se.callista.cadec2025.product.application.port.in.manage.UpdateProductCommand;
import se.callista.cadec2025.product.application.port.out.inventory.ForManagingInventory;
import se.callista.cadec2025.product.application.domain.Inventory;
import se.callista.cadec2025.product.application.port.out.persistence.ForProductPersistence;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.fail;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  private final ProductVariant productVariant = ProductVariant.builder()
      .articleId("product1")
      .name("Product 1")
      .build();

  private final Inventory productInventory = Inventory.builder()
      .articleId("product1")
      .stock(1L)
      .build();

  private final Product product = Product.builder()
      .articleId("product1")
      .name("Product 1")
      .inventory(1L)
      .build();

  @Spy
  private ProductMapper mapper = new ProductMapper();
  @Mock
  private ForProductPersistence productPersistencePort;
  @Mock
  private ForManagingInventory inventoryManagementPort;

  @InjectMocks
  private ProductService service;

  @Test
  void createProduct() {
    ProductVariant newProductVariant = ProductVariant.builder()
        .articleId("newArticle")
        .name("New Article")
        .build();

    service.createProduct(new CreateProductCommand(newProductVariant.getArticleId(), newProductVariant.getName()));

    Mockito.verify(productPersistencePort).createProduct(newProductVariant);
    Mockito.verify(inventoryManagementPort).createInventory(newProductVariant.getArticleId());
  }

  @Test
  void getProducts() {
    Mockito.when(productPersistencePort.getProducts())
        .thenReturn(Collections.singletonList(productVariant));
    Mockito.when(inventoryManagementPort.getInventory(productVariant.getArticleId()))
        .thenReturn(productInventory);

    List<Product> expected = Collections.singletonList(product);
    List<Product> actual = service.getProducts();

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void getProduct() {
    Mockito.when(productPersistencePort.getProduct(product.getArticleId()))
        .thenReturn(productVariant);
    Mockito.when(inventoryManagementPort.getInventory(productVariant.getArticleId()))
        .thenReturn(productInventory);

    Product expected = product;
    Product actual = service.getProduct(new GetProductCommand(product.getArticleId()));

    assertThat(actual).isEqualTo(expected);
  }

  @Test
  void createDuplicateProduct() {
    ProductVariant newProductVariant = ProductVariant.builder()
        .articleId("newArticle")
        .name("New Article")
        .build();

    Mockito.when(productPersistencePort.createProduct(newProductVariant))
        .thenThrow(new RuntimeException("Duplicate"));

    try {
      service.createProduct(
          new CreateProductCommand(newProductVariant.getArticleId(), newProductVariant.getName()));
      fail("RuntimeException expected");
    } catch (RuntimeException expected) {
      // Expected
    }

    Mockito.verifyNoInteractions(inventoryManagementPort);
  }

  @Test
  void updateProduct() {
    ProductVariant updatedProductVariant = ProductVariant.builder()
        .articleId(productVariant.getArticleId())
        .name("Updated name")
        .build();
    Mockito.when(productPersistencePort.getProduct(product.getArticleId()))
        .thenReturn(productVariant);

    service.updateProduct(new UpdateProductCommand(updatedProductVariant.getArticleId(), updatedProductVariant.getName()));

    Mockito.verify(productPersistencePort).updateProduct(updatedProductVariant);
  }
}