package snk.com.shortener.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.utility.DockerImageName;

@TestConfiguration
public class TestContainersConfig {

  @Bean
  public MySQLContainer<?> mySQLContainer() {
    MySQLContainer<?> container =
        new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");
    container.start();
    return container;
  }
}
