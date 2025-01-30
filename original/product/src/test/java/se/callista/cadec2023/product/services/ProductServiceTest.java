package se.callista.cadec2023.product.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.github.database.rider.core.api.dataset.DataSet;
import com.github.database.rider.core.api.dataset.ExpectedDataSet;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.callista.cadec2023.product.annotation.SpringBootDbIntegrationTest;
import se.callista.cadec2023.product.domain.model.Product;
import se.callista.cadec2023.product.persistence.PostgresqlTestContainer;
import se.callista.cadec2023.inventory.model.InventoryValue;

@Testcontainers
@SpringBootDbIntegrationTest
public class ProductServiceTest {

  @Container
  private static final PostgresqlTestContainer POSTGRESQL_CONTAINER = PostgresqlTestContainer.getInstance();

  @Autowired
  private ProductService productService;

  @Autowired
  private JmsTemplate jmsTemplate;

  @MockBean
  private InventoryService inventoryService;

  @Test
  @DataSet(value = {"products.yml"})
  @ExpectedDataSet(value = "expectedProducts.yml", ignoreCols = "id")
  public void createProduct() {

    Product newProduct = Product.builder()
        .articleId("newProduct")
        .name("New Product")
        .build();

    Product product = productService.createProduct(newProduct);

    InventoryValue stockMessage =
        (InventoryValue) jmsTemplate.receiveAndConvert("stock");
    assertThat(stockMessage.getArticleId()).isEqualTo(product.getArticleId());
    assertThat(stockMessage.getStock()).isEqualTo(product.getInventory());
  }

  @Test
  @DataSet(value = {"products.yml"})
  public void getProducts() {

    when(inventoryService.getInventory(anyString()))
        .thenReturn(InventoryValue.builder()
            .stock(1L)
            .build());

    Product product = productService.getProduct("product1");
    assertThat(product.getName()).isEqualTo("Product 1");
    assertThat(product.getInventory()).isEqualTo(1);

    verify(inventoryService, only()).getInventory("product1");

  }

}
