package sainsbury.serversidetest.crawler;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import sainsbury.serversidetest.entities.Product;
import sainsbury.serversidetest.entities.Products;
import sainsbury.serversidetest.entities.Total;
import sainsbury.serversidetest.invoice.service.InvoiceService;

/**
 * Wrapper object on the HTML page with the products list. Universal parsing algorithm for various
 * HTML sources.
 */
public abstract class ProductsPage {

  private static Logger LOGGER = LoggerFactory.getLogger(ProductsPage.class);

  private ProductPage productPage;

  @Autowired
  private CrawlerUtil crawlerUtil;

  @Autowired
  private InvoiceService invoiceService;

  public ProductsPage(ProductPage productPage) {
    Objects.requireNonNull(productPage);
    this.productPage = productPage;
  }

  public Products getProducts(String url) throws Exception {
    return this.getProducts(crawlerUtil.getDocument(url));
  }

  public Products getProducts(Document productsDocument) throws Exception {
    Products products = new Products();
    products.setResults(getResults(productsDocument));
    products.setTotal(createTotal(products.getResults()));
    return products;
  }

  protected List<Product> getResults(Document productsDocument) throws Exception {
    List<Product> productsList = new LinkedList<>();
    Elements products = findProducts(productsDocument);
    if (products == null || products.size() == 0) {
      throw new ParsingException("Products parsing error URL=" + productsDocument.baseUri());
    }
    Exception firstProductException = null;
    for (Element product : products) {
      try {
        String productUrl = getProductUrl(product);
        productsList.add(productPage.getProduct(crawlerUtil.getDocument(productUrl)));
      } catch (Exception e) {
        if (firstProductException == null) {
          firstProductException = e;
        }
        LOGGER.warn(e.getMessage(), e);
      }
    }
    if (firstProductException != null && productsList.isEmpty()) {
      throw firstProductException;
    }
    return productsList;
  }

  protected Total createTotal(List<Product> productsList) {
    Total total = new Total();
    total.setGross(calculateGross(productsList));
    total.setVat(calculateVat(total.getGross()));
    return total;
  }

  protected double calculateGross(List<Product> productsList) {
    return productsList.stream().mapToDouble(product -> product.getUnitPrice()).sum();
  }

  protected Double calculateVat(double gross) {
    return invoiceService.calculateVat(gross);
  }

  protected abstract Elements findProducts(Document document);

  protected abstract String getProductUrl(Element product);
}
