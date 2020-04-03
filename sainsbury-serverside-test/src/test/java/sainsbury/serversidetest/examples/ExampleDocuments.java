package sainsbury.serversidetest.examples;

import java.io.File;
import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class ExampleDocuments {

  public Document getCorrectProducts() throws IOException {
    File productsFile = new File("src/test/resources/www/products.html");
    return Jsoup.parse(productsFile, "UTF-8");
  }

  public Document getCorrectProduct() throws IOException {
    File productsFile = new File("src/test/resources/www/product.html");
    return Jsoup.parse(productsFile, "UTF-8");
  }

  public Document getParsingError() throws IOException {
    File productsFile = new File("src/test/resources/www/parsing-error.html");
    return Jsoup.parse(productsFile, "UTF-8");
  }

}
