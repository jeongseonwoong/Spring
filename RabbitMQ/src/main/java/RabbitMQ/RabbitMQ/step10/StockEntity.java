package RabbitMQ.RabbitMQ.step10;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class StockEntity {

    @Id @GeneratedValue
    private Long id;

    private String userId;
    private int stock;

    private boolean processed;

    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @Builder
    private StockEntity(String userId, int stock, boolean processed, LocalDateTime createdAt, LocalDateTime updatedAt){
        this.userId = userId;
        this.stock = stock;
        this.processed = processed;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static StockEntity from(StockDTO stockDTO){
        return StockEntity.builder()
                .userId(stockDTO.getUserId())
                .stock(stockDTO.getStock())
                .processed(stockDTO.isProcessed())
                .build();
    }
}
