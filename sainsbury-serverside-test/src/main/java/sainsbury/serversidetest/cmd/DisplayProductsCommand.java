package sainsbury.serversidetest.cmd;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import sainsbury.serversidetest.products.service.ProductsService;

@Component
public class DisplayProductsCommand implements CommandLineRunner {

  @Autowired
  private ProductsService productsService;

  @Override
  public void run(String... args) throws Exception {
    System.out.println(productsService.getProducts());
  }

}
