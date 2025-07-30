package concurrency.issue.stock.repository;

import concurrency.issue.stock.domain.Stock;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByProductId(Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.productId = :productId")
    Optional<Stock> findByProductIdWithPessimisticLock(Long productId);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.productId = :productId")
    Optional<Stock> findByProductIdWithOptimisticLock(Long productId);
}
