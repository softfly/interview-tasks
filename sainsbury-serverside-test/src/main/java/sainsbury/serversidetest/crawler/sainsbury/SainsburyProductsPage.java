package sainsbury.serversidetest.crawler.sainsbury;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import sainsbury.serversidetest.crawler.ProductsPage;

@Component("SainsburyProductsPage")
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class SainsburyProductsPage extends ProductsPage {

  public SainsburyProductsPage() {
    super(new SainsburyProductPage());
  }

  @Override
  protected Elements findProducts(Document document) {
    return document.select(".product");
  }

  @Override
  protected String getProductUrl(Element product) {
    return product.select("h3 a[href]").get(0).absUrl("href");
  }

}
