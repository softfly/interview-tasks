package sainsbury.serversidetest.service.products;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = {"classpath:features/ProductsService.feature"})
public class ProductsServiceCucumberTest {

}
