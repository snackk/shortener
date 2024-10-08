package snk.com.shortener.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import snk.com.shortener.domain.ShortUrl;

@Repository
public interface ShortUrlRepository extends JpaRepository<ShortUrl, Long> {

  Optional<ShortUrl> findByShortCode(String shortCode);
}
