package cache.demo;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
@Component
public class AppRunner implements CommandLineRunner {

    private final BookRepository bookRepository;

    public AppRunner(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        log.info(".... Fetching books");
        LocalDateTime startTime = LocalDateTime.now();
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-1234"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-4567"), LocalDateTime.now());
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-1234"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-4567"), LocalDateTime.now());
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-1234"), LocalDateTime.now());
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-1234"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-4567"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbn("isbn-4567"), LocalDateTime.now());
        LocalDateTime endTime = LocalDateTime.now();
        log.info("총 걸린 시간 = {}", Duration.between(startTime,endTime).toMillis());


        log.info(".... Fetching books Using cache");
        startTime = LocalDateTime.now();
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-1234"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-4567"), LocalDateTime.now());
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-1234"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-4567"), LocalDateTime.now());
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-1234"), LocalDateTime.now());
        log.info("isbn-1234 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-1234"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-4567"), LocalDateTime.now());
        log.info("isbn-4567 -->{}, Time -->{}", bookRepository.getTitleByIsbnUsingCache("isbn-4567"), LocalDateTime.now());
        endTime = LocalDateTime.now();
        log.info("총 걸린 시간 = {}", Duration.between(startTime,endTime).toMillis());
    }


}