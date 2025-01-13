package message;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Locale;
import org.junit.jupiter.api.Test;
import org.springframework.context.support.ResourceBundleMessageSource;

public class TestAll {
  @Test
  void simple() {
    var messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");

    String welcome = messageSource.getMessage("welcome.message", new String[] {"Alice"}, Locale.US);
    assertEquals("Welcome, Alice!", welcome);
  }

  @Test
  void utf8() {
    var messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("messages");
    messageSource.setDefaultEncoding("UTF-8"); // この設定がないと文字化けする

    String welcome =
        messageSource.getMessage("welcome.message", new String[] {"太郎"}, Locale.JAPANESE);
    assertEquals("ようこそ、太郎さん！", welcome);
  }
}
