package com.example.controller;

import java.net.URI;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("uri")
public class UriController {
  // UriComponentsBuilder による構築
  @GetMapping("simple")
  public String getSimple(UriComponentsBuilder uriBuilder) { // パス変数への埋め込み
    URI uri =
        uriBuilder
            .path("foo/{name}")
            .buildAndExpand("X & Y ^ Z") // パス変数への埋め込み
            .encode() // URL エンコーディング
            .toUri();
    return uri.toString();
    // -> http://localhost:8080/foo/X%20&%20Y%20%5E%20Z
  }

  // MvcUriComponentsBuilder による構築
  @GetMapping("mvc")
  public String getMvc(UriComponentsBuilder uriBuilder) {
    URI uri =
        MvcUriComponentsBuilder.relativeTo(uriBuilder) // 引数の uriBuilder をベースに構築する
            .withMethodCall(
                MvcUriComponentsBuilder.on(BookController.class).getBook(1234L)
                // ハンドラに設定されたパスを返す
                // 引数をパス変数に埋め込む
                )
            .build()
            .encode()
            .toUri();
    return uri.toString();
    // -> http://localhost:8080/book/A%20&%20B%20%5E%20C
  }

  // リダイレクト先 URL を構築するのに使えそう
  // cf. https://qiita.com/rubytomato@github/items/8d132dec042f695e50f6
}
