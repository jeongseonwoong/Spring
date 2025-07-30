package concurrency.issue.stock.facade;

import concurrency.issue.stock.domain.Stock;
import concurrency.issue.stock.repository.StockRepository;
import concurrency.issue.stock.service.OptimisticLockStockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@SpringBootTest
class OptimisticLockStockFacadeTest {

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private OptimisticLockStockFacade optimisticLockStockFacade;

    @BeforeEach
    public void before(){
        stockRepository.saveAndFlush(new Stock(1L,100L));
    }

    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    public void OptimisticLock_동시에_100개의_재고감소_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i=0; i< threadCount; i++) {
            executorService.submit(()->{
                try {
                    optimisticLockStockFacade.decrease(1L,1L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                } finally {
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