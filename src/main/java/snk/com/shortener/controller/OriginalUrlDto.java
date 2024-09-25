package snk.com.shortener.controller;

import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class OriginalUrlDto {

  @URL(message = "Provide a valid URL.")
  private String originalUrl;
}
