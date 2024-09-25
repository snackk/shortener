package snk.com.shortener;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import snk.com.shortener.config.TestContainersConfig;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestContainersConfig.class)
@ActiveProfiles("test")
class ShortenerApplicationTests {

  @Test
  void theMotherOfAllTests() {}
}
