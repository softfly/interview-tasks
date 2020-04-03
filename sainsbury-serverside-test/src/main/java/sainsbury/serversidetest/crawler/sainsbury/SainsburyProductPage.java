package sainsbury.serversidetest.crawler.sainsbury;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import sainsbury.serversidetest.crawler.ProductPage;

public class SainsburyProductPage extends ProductPage {

  @Override
  protected String getTitle(Document document) {
    return document.select("h1").html();
  }

  @Override
  protected Integer getKcalPer100g(Document document) {
    Elements elements = document.select(".nutritionLevel1");
    if (elements != null && elements.size() > 0) {
      return Integer.parseInt(elements.get(0).html().replaceAll("kcal", ""));
    }
    return null;
  }

  @Override
  protected Double getUnitPrice(Document document) {
    return Double.parseDouble(
        document.select(".productSummary .pricePerUnit").text().replaceAll("£", "")
            .replaceAll("/unit", ""));
  }

  @Override
  protected String getDescription(Document document) {
    return document.select(".productText").get(0).text();
  }

}
