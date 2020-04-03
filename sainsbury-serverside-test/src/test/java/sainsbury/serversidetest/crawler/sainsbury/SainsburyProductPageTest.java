package sainsbury.serversidetest.crawler.sainsbury;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.IOException;
import org.junit.jupiter.api.Test;
import sainsbury.serversidetest.crawler.ParsingException;
import sainsbury.serversidetest.crawler.ProductPage;
import sainsbury.serversidetest.entities.Product;
import sainsbury.serversidetest.examples.ExampleDocuments;

public class SainsburyProductPageTest {

  private ExampleDocuments exampleDocuments = new ExampleDocuments();

  private ProductPage productPage = new SainsburyProductPage();

  @Test
  public void ParsedProduct() throws ParsingException, IOException {
    Product product = productPage.getProduct(exampleDocuments.getCorrectProduct());
    assertEquals("Sainsbury's Strawberries 400g", product.getTitle());
    assertEquals(33, product.getKcalPer100g());
    assertEquals(1.75, product.getUnitPrice());
    assertEquals("by Sainsbury's strawberries Line2", product.getDescription());
  }

}
