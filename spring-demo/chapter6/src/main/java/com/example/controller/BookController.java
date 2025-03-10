package com.example.controller;

import com.example.resource.BookResource;
import java.time.LocalDate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("book")
public class BookController {
  @PostMapping("{bookId}")
  public ResponseEntity<BookResource> getBook(@PathVariable("bookId") Long bookId) {
    return ResponseEntity.ok(new BookResource(bookId, "TITLE", LocalDate.now()));

    // 返り値が final クラスの場合、proxy が作れず MvcUriComponentsBuilder がエラーを吐く
    // その場合、ResponseEntity クラスでラップすればいい
    // cf. https://stackoverflow.com/questions/38751956/
  }
}
