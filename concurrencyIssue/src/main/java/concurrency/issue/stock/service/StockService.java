package concurrency.issue.stock.service;

import concurrency.issue.stock.domain.Stock;
import concurrency.issue.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public synchronized void decrease(Long productId, Long quantity){
        //stock 조회
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() ->
                new RuntimeException("해당하는 stock이 존재하지 않습니다."));
        //재고 감소
        stock.decrease(quantity);
        // 갱신되 값을 저장
        stockRepository.save(stock);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseByLettuce(Long productId, Long quantity){
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() ->
                new RuntimeException("해당하는 stock이 존재하지 않습니다."));
        stock.decrease(quantity);
        stockRepository.save(stock);
    }

    /**
     * 부모 transaction의 존재 여부와 상관없이, 새로운 transaction을 만들어 적용한다.
     * 기존에 실행하던 부모 transaction이 있는 경우 → 부모 transaction은 자식 transaction이 끝날 때까지 대기한다.
     * 자식 transaction과 부모 transaction의 rollback 여부는 서로 독립적이다.
     * 자식 transaction이 rollback 되더라도, 부모 transaction은 rollback 되지 않는다.
     * 자식 transaction이 정상적으로 종료되고 부모 transaction이 rollback 되어도, 자식 transaction은 rollback 되지 않는다.
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decreaseByNamedLock(Long productId, Long quantity){
        //stock 조회
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() ->
                new RuntimeException("해당하는 stock이 존재하지 않습니다."));
        //재고 감소
        stock.decrease(quantity);
        // 갱신되 값을 저장
        stockRepository.save(stock);
    }
}
