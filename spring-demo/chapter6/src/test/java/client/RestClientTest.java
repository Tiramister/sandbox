package client;

import static org.junit.jupiter.api.Assertions.assertThrows;

import com.example.resource.BookResource;
import java.time.Duration;
import org.junit.jupiter.api.Test;
import org.springframework.boot.http.client.ClientHttpRequestFactoryBuilder;
import org.springframework.boot.http.client.ClientHttpRequestFactorySettings;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;

// RestClient は同期、WebClient は非同期
// https://docs.spring.io/spring-framework/reference/integration/rest-clients.html
public class RestClientTest {
  @Test
  public void rest() {
    // ヘッダーなども設定できる
    RestClient client = RestClient.builder().baseUrl("http://localhost:8080").build();

    BookResource resource =
        client
            .post() // メソッド
            .uri("book/1234") // baseUrl からの相対パス
            .contentType(MediaType.APPLICATION_JSON) // Content-Type ヘッダー
            .header("Authorization", "Bearer token") // 任意ヘッダー
            .retrieve() // リクエスト実行
            .body(BookResource.class); // 型変換

    System.out.println(resource);
  }

  @Test
  public void restError() {
    Throwable exception =
        assertThrows(
            RuntimeException.class,
            () -> {
              RestClient client = RestClient.builder().baseUrl("http://localhost:8080").build();

              BookResource resource =
                  client
                      .post()
                      .uri("apples") // 間違った URI
                      .retrieve()
                      .onStatus( // エラーハンドリング
                          HttpStatusCode::is4xxClientError,
                          (request, response) -> {
                            throw new RuntimeException(response.getStatusText());
                          })
                      .body(BookResource.class); // 型変換 (到達しない)
            });
    System.out.println(exception.getMessage());
  }

  @Test
  public void restTimeout() {
    // https://www.baeldung.com/spring-rest-timeout#2-restclient-timeout
    // ClientHttpRequestFactories は非推奨なので ClientHttpRequestFactoryBuilder を使う
    var requestFactory =
        ClientHttpRequestFactoryBuilder.simple()
            .build(
                ClientHttpRequestFactorySettings.defaults()
                    .withConnectTimeout(Duration.ofMillis(500))
                    .withReadTimeout(Duration.ofMillis(500)));

    // RestClient 構築時に渡す
    RestClient client =
        RestClient.builder()
            .baseUrl("http://localhost:8080")
            .requestFactory(requestFactory)
            .build();

    // 後は通常と同じ
    BookResource resource = client.post().uri("book/5678").retrieve().body(BookResource.class);

    System.out.println(resource);
  }
}
