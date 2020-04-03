package sainsbury.serversidetest.service.products;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.IOException;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sainsbury.serversidetest.crawler.CrawlerUtil;
import sainsbury.serversidetest.examples.ExampleDocuments;
import sainsbury.serversidetest.products.service.ProductsService;

@SpringBootTest
public class ProductsServiceStepDef {

  private ExampleDocuments exampleDocuments = new ExampleDocuments();

  @Autowired
  private ProductsService productsService;

  @Autowired
  @MockBean
  private CrawlerUtil crawlerUtil;

  private String output;

  @Before
  public void setUp() {
    MockitoAnnotations.initMocks(this);
  }

  @After
  public void resetMocks() {
    Mockito.reset(crawlerUtil);
  }

  @When(value = "^Call getProducts$")
  public void Call_getProducts() {
    output = productsService.getProducts();
  }

  @Given(value = "^Valid HTML pages$")
  public void Valid_HTML_pages() throws IOException {
    Mockito.when(crawlerUtil.getDocument(Mockito.anyString()))
        .thenReturn(exampleDocuments.getCorrectProducts(), exampleDocuments.getCorrectProduct());
  }

  @Given(value = "^Error connecting to the site$")
  public void Error_connecting_to_the_site() throws IOException {
    Mockito.when(crawlerUtil.getDocument(Mockito.anyString()))
        .thenThrow(java.net.UnknownHostException.class);
  }

  @Given(value = "^Not 200 HTTP code$")
  public void Not_200_HTTP_code() throws IOException {
    Mockito.when(crawlerUtil.getDocument(Mockito.anyString()))
        .thenThrow(org.jsoup.HttpStatusException.class);
  }

  @Given(value = "^Not valid products HTML scheme$")
  public void Not_valid_products_HTML_scheme() throws IOException {
    Mockito.when(crawlerUtil.getDocument(Mockito.anyString()))
        .thenReturn(exampleDocuments.getParsingError());
  }

  @Given(value = "^Not valid product HTML scheme$")
  public void Not_valid_product_HTML_scheme() throws IOException {
    Mockito.when(crawlerUtil.getDocument(Mockito.anyString()))
        .thenReturn(exampleDocuments.getCorrectProducts(),
            exampleDocuments.getParsingError());
  }

  @Then("^Return products list$")
  public void Return_products_list() {
    assertThat(output).contains("results");
    assertThat(output).contains("Sainsbury's Strawberries 400g");
  }

  @Then("^Return (.*) '(.*)'$")
  public void Return_ERROR0003_product(String error, String message) {
    assertThat(output).contains(error);
    assertThat(output).contains(message);
  }
}
