package se.callista.cadec2023.product.controller;

import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import se.callista.cadec2023.product.domain.model.Product;
import se.callista.cadec2023.product.services.ProductService;

@WebMvcTest(ProductApiController.class)
class ProductApiControllerTest {

  @Autowired
  private MockMvc mvc;

  @MockBean
  private ProductService productService;

  @Test
  void getProduct() throws Exception {

    Product product = Product.builder()
        .productId(1L)
        .articleId("product1")
        .name("Product Name")
        .build();

    given(productService.getProduct(product.getArticleId())).willReturn(product);

    mvc.perform(MockMvcRequestBuilders.get("/products/" + product.getProductId()))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", is(product.getName())));

    verify(productService).getProduct(product.getArticleId());
  }

}