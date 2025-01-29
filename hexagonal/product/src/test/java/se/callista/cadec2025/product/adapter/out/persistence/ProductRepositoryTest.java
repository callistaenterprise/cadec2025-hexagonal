package se.callista.cadec2025.product.adapter.out.persistence;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.dataset.DataSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.callista.cadec2025.product.adapter.annotation.SpringBootDbIntegrationTest;

@Testcontainers
@SpringBootDbIntegrationTest
class ProductRepositoryTest {

  @Container
  @ServiceConnection
  private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
      new PostgreSQLContainer<>("postgres:latest");

  @Autowired
  private ProductRepository productRepository;

  @Test
  @DataSet(value = {"products.yml"})
  void findByArticleId() {
    Optional<ProductEntity> product = productRepository.findByArticleId("product1");
    assertThat(product).isPresent();
    assertThat(product.get().getName()).isEqualTo("Product 1");
  }

}