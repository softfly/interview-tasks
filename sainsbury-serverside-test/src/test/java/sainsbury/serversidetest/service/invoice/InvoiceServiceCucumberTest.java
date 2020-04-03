package sainsbury.serversidetest.service.invoice;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/InvoiceService.feature"})
public class InvoiceServiceCucumberTest {

}
