package concurrency.issue.stock.facade;

import concurrency.issue.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class RedissonLockStockFacade {

    private final RedissonClient redissonClient;

    private final StockService stockService;

    public void decrease(Long productId, Long quantity) {
        RLock lock = redissonClient.getLock(productId.toString());

        try {
            //waitTime=10 leaseTime 점유시간=1
            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if(!available){
                System.out.println("lock확득실패");
                return;
            }
            stockService.decreaseByRedisson(productId,quantity);
        } catch (InterruptedException e){
            throw new RuntimeException(e);
        }finally {
            lock.unlock();
        }
    }
}
