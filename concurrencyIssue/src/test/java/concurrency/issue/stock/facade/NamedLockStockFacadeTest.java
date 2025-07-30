package concurrency.issue.stock.facade;

import concurrency.issue.stock.domain.Stock;
import concurrency.issue.stock.repository.StockRepository;
import concurrency.issue.stock.service.PessimisticLockStockService;
import concurrency.issue.stock.service.StockService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class NamedLockStockFacadeTest {

    @Autowired
    private StockService stockService;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private NamedLockStockFacade namedLockStockFacade;

    @BeforeEach
    public void before(){
        stockRepository.saveAndFlush(new Stock(1L,100L));
    }

    @AfterEach
    public void after(){
        stockRepository.deleteAll();
    }

    @Test
    public void namedLockStockFacade_동시에_100개의_재고감소_요청() throws InterruptedException {
        int threadCount = 100;
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i=0; i< threadCount; i++) {
            executorService.submit(()->{
                try {
                    namedLockStockFacade.decrease(1L,1L);
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