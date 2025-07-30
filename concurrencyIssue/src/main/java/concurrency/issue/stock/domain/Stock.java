package concurrency.issue.stock.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;

    private Long quantity;

    public void decrease(Long quantity){
        if(this.quantity - quantity < 0)
            throw new RuntimeException("재고는 0개 미만일 수 없습니다.");

        this.quantity -= quantity;
    }

    public Stock(Long productId, Long quantity){
        this.productId = productId;
        this.quantity = quantity;
    }

}
