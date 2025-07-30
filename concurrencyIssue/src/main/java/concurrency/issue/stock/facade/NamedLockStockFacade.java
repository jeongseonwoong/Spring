package concurrency.issue.stock.facade;

import concurrency.issue.stock.repository.LockRepository;
import concurrency.issue.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class NamedLockStockFacade {

    private final LockRepository lockRepository;

    private final StockService stockService;

    @Transactional
    public void decrease(Long productId, Long quantity) {
        try {
            lockRepository.getLock(productId.toString());
            stockService.decreaseByNamedLock(productId, quantity);
        } finally {
            lockRepository.releaseLock(productId.toString());
        }
    }
}
