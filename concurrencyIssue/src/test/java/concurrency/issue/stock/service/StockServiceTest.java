package concurrency.issue.stock.service;


import concurrency.issue.stock.domain.Stock;
import concurrency.issue.stock.repository.StockRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class StockServiceTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private PessimisticLockStockService pessimisticLockStockService;

    @BeforeEach
    public void before(){
        stockRepository.saveAndFlush(new Stock(1L,100L));
    }

    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    public void 재고감소(){
        stockService.decrease(1L,1L);

        Stock stock = stockRepository.findByProductId(1L).orElseThrow();

        Assertions.assertEquals(99,stock.getQuantity());
    }

    @Test
    public void 동시에_100개의_재고감소_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i=0; i< threadCount; i++) {
            executorService.submit(()->{
                try {
                    stockService.decrease(1L,1L);
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //race condition 발생 synchronized 사용하려면 @Transactional 사용하면 안댐. transaction에 의해 db에 커밋되기 전에 다른 스레드가 실행될 수 있기때문에
        Stock stock = stockRepository.findByProductId(1L).orElseThrow();
//        org.assertj.core.api.Assertions.assertThat(stock.getQuantity()).isEqualTo(0);
        org.assertj.core.api.Assertions.assertThat(stock.getQuantity()).isNotEqualTo(0);
    }

    @Test
    public void PessimisticLock_동시에_100개의_재고감소_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i=0; i< threadCount; i++) {
            executorService.submit(()->{
                try {
                    pessimisticLockStockService.decrease(1L,1L);
                }finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        //race condition 발생
        Stock stock = stockRepository.findByProductId(1L).orElseThrow();
        org.assertj.core.api.Assertions.assertThat(stock.getQuantity()).isEqualTo(0);
    }
}