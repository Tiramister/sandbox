package com.example.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

// ResponseEntityExceptionHandler: REST API での例外ハンドラを作りやすくするクラス
@ControllerAdvice
public class ExceptionHandler extends ResponseEntityExceptionHandler {
  // Spring Framework 独自の例外のハンドリング
  // handleExceptionInternal 以外にも、例外クラス毎にハンドラメソッドが用意されている
  // https://spring.pleiades.io/spring-framework/docs/current/javadoc-api/org/springframework/web/servlet/mvc/method/annotation/ResponseEntityExceptionHandler.html
  @Override
  protected ResponseEntity<Object> handleExceptionInternal(
      Exception ex,
      Object body,
      HttpHeaders headers,
      HttpStatusCode statusCode,
      WebRequest request) {
    return super.handleExceptionInternal(ex, "Oops! :(", headers, statusCode, request);
  }
}
