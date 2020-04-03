package sainsbury.serversidetest.crawler;

import java.io.IOException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component()
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class CrawlerUtil {

  public Document getDocument(String url) throws IOException {
    return Jsoup.connect(url).get();
  }

}
