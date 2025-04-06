package cache.demo;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@RequiredArgsConstructor
public class BookDataRepository implements BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public String getTitleByIsbn(String isbn) {
        return jdbcTemplate.queryForObject("select title from Book where isbn = ?", String.class, isbn);
    }

    @Override
    @Cacheable("books")
    public String getTitleByIsbnUsingCache(String isbn) {
        return jdbcTemplate.queryForObject("select title from Book where isbn = ?", String.class, isbn);
    }

}
