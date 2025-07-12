package RabbitMQ.RabbitMQ.step9;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
public class StockDTO {
    private String userId;
    private int stock;

    private boolean processed;

    public StockDTO(StockEntity stockEntity){
        this.userId = stockEntity.getUserId();
        this.stock = stockEntity.getStock();
        this.processed = stockEntity.isProcessed();
    }

}
