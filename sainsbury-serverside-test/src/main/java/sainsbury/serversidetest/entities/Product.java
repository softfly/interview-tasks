package sainsbury.serversidetest.entities;

/**
 * Entity
 */
public class Product {

  String title;

  Integer kcalPer100g;

  Double unitPrice;

  String description;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getKcalPer100g() {
    return kcalPer100g;
  }

  public void setKcalPer100g(Integer kcalPer100g) {
    this.kcalPer100g = kcalPer100g;
  }

  public Double getUnitPrice() {
    return unitPrice;
  }

  public void setUnitPrice(Double unitPrice) {
    this.unitPrice = unitPrice;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

}
