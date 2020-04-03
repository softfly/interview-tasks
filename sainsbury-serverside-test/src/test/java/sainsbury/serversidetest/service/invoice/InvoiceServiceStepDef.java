package sainsbury.serversidetest.service.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import sainsbury.serversidetest.invoice.service.InvoiceService;

@SpringBootTest
public class InvoiceServiceStepDef {

  private InvoiceService invoiceService = new InvoiceService();

  private Double grossInput;

  private Double vatOutput;

  @Given(value = "Gross value {double}")
  public void Gross_value(Double gross) {
    this.grossInput = gross;
  }

  @When(value = "^Call calculateVat$")
  public void Call_calculateVat() {
    vatOutput = invoiceService.calculateVat(grossInput);
  }

  @Then("Return VAT value {double}")
  public void Return_VAT_value(Double vat) {
    assertEquals(vatOutput, vatOutput);
  }

}
