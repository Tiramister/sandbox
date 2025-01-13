package resource;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import org.example.resource.AppConfig;
import org.example.resource.Greeting;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

public class TestAll {
  @Test
  void classPathResource() throws IOException {
    Resource resource = new ClassPathResource("greeting.txt");

    String content = resource.getContentAsString(StandardCharsets.UTF_8);
    assertEquals("hello", content);
  }

  @Test
  void resourceLoader() throws IOException {
    ResourceLoader resourceLoader = new DefaultResourceLoader();
    Resource resource = resourceLoader.getResource("classpath:greeting.txt");

    String content = resource.getContentAsString(StandardCharsets.UTF_8);
    assertEquals("hello", content);
  }

  @Test
  void injection() throws IOException {
    var context = new AnnotationConfigApplicationContext(AppConfig.class);
    var greeting = context.getBean(Greeting.class);

    var content = greeting.resource.getContentAsString(StandardCharsets.UTF_8);
    assertEquals("hello", content);
  }
}
