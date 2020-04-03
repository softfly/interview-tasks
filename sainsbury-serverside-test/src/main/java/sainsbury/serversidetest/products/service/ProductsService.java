package sainsbury.serversidetest.products.service;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import sainsbury.serversidetest.crawler.ParsingException;
import sainsbury.serversidetest.crawler.ProductsPage;
import sainsbury.serversidetest.entities.Products;
import sainsbury.serversidetest.errors.ErrorMessage;
import sainsbury.serversidetest.errors.ErrorMessagesFactory;

@Service
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class ProductsService {

  private static final String URL =
      "https://jsainsburyplc.github.io/serverside-test/site/www.sainsburys.co.uk/webapp/wcs/stores/servlet/gb/groceries/berries-cherries-currants6039.html";

  private static Logger LOGGER = LoggerFactory.getLogger(ProductsService.class);

  @Autowired
  @Qualifier("SainsburyProductsPage")
  private ProductsPage productsPage;

  private ObjectMapper objectMapper = initObjectMapper();

  public String getProducts() {
    try {
      try {
        Products products = productsPage.getProducts(URL);
        return objectMapper.writeValueAsString(products);
      } catch (org.jsoup.HttpStatusException e) {
        return objectMapper.writeValueAsString(ErrorMessagesFactory.WRONG_HTTP_STATUS);
      } catch (java.net.UnknownHostException e) {
        ErrorMessage errorMessage = ErrorMessagesFactory.UNKNOWN_HOST.createErrorMessage();
        errorMessage.setMessage(errorMessage.getMessage() + " URL=" + URL);
        return objectMapper.writeValueAsString(errorMessage);
      } catch (ParsingException e) {
        LOGGER.warn(e.getMessage(), e);
        ErrorMessage errorMessage = ErrorMessagesFactory.PARSING_ERROR.createErrorMessage();
        errorMessage.setMessage(e.getMessage());
        return objectMapper.writeValueAsString(errorMessage);
      } catch (Exception e) {
        LOGGER.warn(e.getMessage(), e);
        return objectMapper.writeValueAsString(ErrorMessagesFactory.UNKNOWN);
      }
    } catch (JsonProcessingException e) {
      LOGGER.warn(e.getMessage(), e);
      return ErrorMessagesFactory.UNKNOWN.getMessage();
    }
  }

  protected ObjectMapper initObjectMapper() {
    ObjectMapper objectMapper = new ObjectMapper();
    objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    objectMapper.setSerializationInclusion(Include.NON_NULL);
    objectMapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);
    return objectMapper;
  }

}
