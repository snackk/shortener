package snk.com.shortener.controller;

import jakarta.validation.Valid;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import snk.com.shortener.service.UrlShortenerService;

@RestController
@RequestMapping("/api/urls")
public class UrlShortenerController {

  private final UrlShortenerService service;

  @Autowired
  public UrlShortenerController(UrlShortenerService service) {
    this.service = service;
  }

  @PostMapping
  public ResponseEntity<String> shortenUrl(@Valid @RequestBody OriginalUrlDto originalUrlDto) {
    String shortCode = service.shortenUrl(originalUrlDto.getOriginalUrl());
    return ResponseEntity.ok(shortCode);
  }

  @GetMapping("/{shortCode}")
  public ResponseEntity<Void> redirectToOriginalUrl(@PathVariable String shortCode) {
    String originalUrl = service.getOriginalUrl(shortCode);
    return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
  }
}
