package RabbitMQ.RabbitMQ.step9;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

@RestController
@RequestMapping("/api/message")
@RequiredArgsConstructor
public class TransactionalController {
    private final MessageProducer messageProducer;

    @PostMapping
    public ResponseEntity<String> sendMessage(@RequestBody StockDTO stockDTO, @RequestParam(required = false,defaultValue = "success") String testCase) {
        System.out.println("Send message: " + stockDTO);
        try{
            messageProducer.sendMessage(stockDTO,testCase);
            return ResponseEntity.ok("Message sent Successfully");
        }catch (RuntimeException e){
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body("MQ 트랜잭션 실패: " + e.getMessage());
        }
    }

}
