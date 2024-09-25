package snk.com.shortener.service;

import java.security.SecureRandom;
import java.util.stream.Collectors;

public class FixedCodeGenerator {

  private static final String CHARACTERS =
      "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
  private static final int FIXED_LENGTH = 10;
  private static final SecureRandom secureRandom = new SecureRandom();

  public static String generateRandomString() {
    return secureRandom
        .ints(FIXED_LENGTH, 0, CHARACTERS.length())
        .mapToObj(CHARACTERS::charAt)
        .map(Object::toString)
        .collect(Collectors.joining());
  }
}
