package sainsbury.serversidetest.crawler.sainsbury;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import sainsbury.serversidetest.crawler.ProductsPage;
import sainsbury.serversidetest.entities.Products;
import sainsbury.serversidetest.examples.ExampleDocuments;

@SpringBootTest
public class SainsburyProductsPageTest {

  private ExampleDocuments exampleDocuments = new ExampleDocuments();

  @Autowired
  @Qualifier("SainsburyProductsPage")
  private ProductsPage productsPage;

  @Test
  public void ParsedProducts() throws IOException, Exception {
    Products products = productsPage.getProducts(exampleDocuments.getCorrectProducts());
    assertNotNull(products.getResults());
    assertEquals(17, products.getResults().size());
    assertNotNull(products.getTotal());
    assertNotNull(products.getTotal().getGross());
    assertNotNull(products.getTotal().getVat());
  }

}
