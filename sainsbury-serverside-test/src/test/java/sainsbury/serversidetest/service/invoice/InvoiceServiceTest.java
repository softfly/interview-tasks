package sainsbury.serversidetest.service.invoice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import sainsbury.serversidetest.invoice.service.InvoiceService;

public class InvoiceServiceTest {

  private InvoiceService invoiceService = new InvoiceService();

  @Test
  public void CalculatedVat() {
    assertEquals(28.89, invoiceService.calculateVat(144.44));
    assertEquals(28.88, invoiceService.calculateVat(144.41));
  }

}
