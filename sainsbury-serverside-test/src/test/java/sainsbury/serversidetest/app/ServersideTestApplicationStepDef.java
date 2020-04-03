package sainsbury.serversidetest.app;

import static org.assertj.core.api.Assertions.assertThat;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import sainsbury.serversidetest.crawler.CrawlerUtil;
import sainsbury.serversidetest.examples.ExampleDocuments;

@SpringBootTest
public class ServersideTestApplicationStepDef {

  private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
  private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
  private final PrintStream originalOut = System.out;
  private final PrintStream originalErr = System.err;
  private ExampleDocuments exampleDocuments = new ExampleDocuments();
  @Autowired
  @MockBean
  private CrawlerUtil crawlerUtil;
  @Autowired
  private CommandLineRunner serversideTestApplication;

  @Before
  public void setUp() {
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }

  @After
  public void restoreStreams() {
    System.setOut(originalOut);
    System.setErr(originalErr);
  }

  @When(value = "^Run the application from the command prompt$")
  public void Run_application_from_command_prompt() throws Exception {
    Mockito.when(crawlerUtil.getDocument(Mockito.anyString()))
        .thenReturn(exampleDocuments.getCorrectProducts(), exampleDocuments.getCorrectProduct());
    serversideTestApplication.run();
  }

  @Then("^Display products list$")
  public void Display_products_list() {
    assertThat(outContent.toString()).contains("results");
    assertThat(outContent.toString()).contains("Sainsbury's Strawberries 400g");
  }

}
