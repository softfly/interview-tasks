package sainsbury.serversidetest.invoice.service;

import java.math.BigDecimal;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class InvoiceService {

  private static final BigDecimal VAT_PERCENT = BigDecimal.valueOf(0.2);

  public Double calculateVat(double gross) {
    return VAT_PERCENT.multiply(BigDecimal.valueOf(gross)).setScale(2, BigDecimal.ROUND_HALF_UP)
        .doubleValue();
  }

}
