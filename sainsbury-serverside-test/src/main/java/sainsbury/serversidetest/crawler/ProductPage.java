package sainsbury.serversidetest.crawler;

import java.math.BigDecimal;
import java.math.RoundingMode;
import org.jsoup.nodes.Document;
import sainsbury.serversidetest.entities.Product;

/**
 * Wrapper on the HTML page with the single product. Universal parsing algorithm for various HTML
 * sources.
 */
public abstract class ProductPage {

  public Product getProduct(Document document) throws ParsingException {
    try {
      Product product = new Product();
      product.setTitle(getTitle(document));
      product.setKcalPer100g(getKcalPer100g(document));
      BigDecimal unitPrice = new BigDecimal(getUnitPrice(document))
          .setScale(2, RoundingMode.HALF_UP);
      product.setUnitPrice(unitPrice.doubleValue());
      product.setDescription(getDescription(document));
      return product;
    } catch (Exception e) {
      throw new ParsingException("Product parsing error URL=" + document.baseUri(), e);
    }
  }

  protected abstract String getTitle(Document document);

  protected abstract Integer getKcalPer100g(Document document);

  protected abstract Double getUnitPrice(Document document);

  protected abstract String getDescription(Document document);

}
