package concurrency.issue.stock.service;

import concurrency.issue.stock.domain.Stock;
import concurrency.issue.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;

    @Transactional
    public void decrease(Long productId, Long quantity){
        //stock 조회
        Stock stock = stockRepository.findByProductId(productId).orElseThrow(() ->
                new RuntimeException("해당하는 stock이 존재하지 않습니다."));
        //재고 감소
        stock.decrease(quantity);
        // 갱신되 값을 저장
        stockRepository.save(stock);
    }
}
