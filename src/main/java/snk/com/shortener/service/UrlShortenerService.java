package snk.com.shortener.service;

import static snk.com.shortener.service.FixedCodeGenerator.generateRandomString;

import java.time.Clock;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import snk.com.shortener.domain.ShortUrl;
import snk.com.shortener.repository.ShortUrlRepository;

@Service
public class UrlShortenerService {

  private static final long DEFAULT_EXPIRY_DAYS = 1L;

  private final ShortUrlRepository shortUrlRepository;
  private final Clock clock;

  @Autowired
  public UrlShortenerService(ShortUrlRepository shortUrlRepository, Clock clock) {
    this.shortUrlRepository = shortUrlRepository;
    this.clock = clock;
  }

  public String shortenUrl(String originalUrl) {
    LocalDateTime localDateTime = LocalDateTime.now(clock);
    ShortUrl shortUrl =
        ShortUrl.builder()
            .originalUrl(originalUrl)
            .shortCode(generateShortCode())
            .createdAt(localDateTime)
            .expiresAt(localDateTime.plusDays(DEFAULT_EXPIRY_DAYS))
            .build();
    shortUrlRepository.save(shortUrl);
    return shortUrl.getShortCode();
  }

  public String getOriginalUrl(String shortCode) {
    ShortUrl shortUrl =
        shortUrlRepository
            .findByShortCode(shortCode)
            .orElseThrow(() -> new RuntimeException("Short URL not found"));
    shortUrl.setClickCount(shortUrl.getClickCount() + 1);
    shortUrlRepository.save(shortUrl);
    return shortUrl.getOriginalUrl();
  }

  private String generateShortCode() {
    return generateRandomString();
  }
}
