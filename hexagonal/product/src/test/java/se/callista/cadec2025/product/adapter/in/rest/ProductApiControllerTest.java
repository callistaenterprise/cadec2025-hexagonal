package se.callista.cadec2025.product.adapter.in.rest;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.callista.cadec2025.product.application.port.in.create.ForCreatingProducts;
import se.callista.cadec2025.product.application.port.in.manage.ForManagingProducts;
import se.callista.cadec2025.product.application.port.in.manage.GetProductCommand;
import se.callista.cadec2025.product.application.domain.Product;

@WebMvcTest(ProductApiController.class)
@Import(ProductValueMapper.class)
class ProductApiControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockitoBean
  private ForCreatingProducts productCreationPort;

  @MockitoBean
  private ForManagingProducts productManagementPort;

  @Test
  void getProduct() throws Exception {

    Product product = Product.builder()
        .articleId("productId")
        .name("Product Name")
        .inventory(1L)
        .build();

    given(productManagementPort.getProduct(new GetProductCommand(product.getArticleId()))).willReturn(product);

    mvc.perform(MockMvcRequestBuilders.get("/products/" + product.getArticleId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(product.getName())));

    verify(productManagementPort).getProduct(new GetProductCommand(product.getArticleId()));
  }

}