package concurrency.issue.stock.service;

import concurrency.issue.stock.domain.Stock;
import concurrency.issue.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OptimisticLockStockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long productId, Long quantity){
        Stock stock = stockRepository.findByProductIdWithOptimisticLock(productId).orElseThrow();
        stock.decrease(quantity);
        stockRepository.save(stock);
    }
}
