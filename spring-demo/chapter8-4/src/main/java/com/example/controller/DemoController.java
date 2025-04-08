package com.example.controller;

import com.example.model.Credential;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.support.SessionStatus;

@RestController
@SessionAttributes("credential") // credential が保存対象になる
public class DemoController {
  // e.g. http://localhost:8080/login?userId=mister&password=1234
  @GetMapping("login")
  public void login(
      @RequestParam("userId") String userId,
      @RequestParam("password") String password,
      Model model) {
    model.addAttribute("credential", new Credential(userId, password));
  }

  // e.g. http://localhost:8080/access
  @GetMapping("access")
  public String access(@ModelAttribute("credential") Credential credential) {
    return String.format("Hello, %s! (Password: %s)", credential.userId(), credential.password());
  }

  // e.g. http://localhost:8080/logout
  @GetMapping("logout")
  public String logout(
      @ModelAttribute("credential") Credential credential, SessionStatus sessionStatus) {
    sessionStatus.setComplete();
    // このリクエスト内ではアクセスできることに注意 (次からアクセスできなくなる)
    return String.format("Logout successful (User ID: %s)", credential.userId());
  }
}
