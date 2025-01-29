package se.callista.cadec2023.product.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.database.rider.core.api.dataset.DataSet;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import se.callista.cadec2023.product.annotation.SpringBootDbIntegrationTest;
import se.callista.cadec2023.product.domain.entity.ProductVariant;
import se.callista.cadec2023.product.persistence.PostgresqlTestContainer;

@Testcontainers
@SpringBootDbIntegrationTest
class ProductVariantRepositoryTest {

  @Container
  private static final PostgresqlTestContainer POSTGRESQL_CONTAINER = PostgresqlTestContainer.getInstance();

  @Autowired
  private ProductVariantRepository productVariantRepository;

  @Test
  @DataSet(value = {"products.yml"})
  void findById() {

    Optional<ProductVariant> product = productVariantRepository.findById(1L);
    assertThat(product).isPresent();
    assertThat(product.get().getName()).isEqualTo("Product 1");

  }

}