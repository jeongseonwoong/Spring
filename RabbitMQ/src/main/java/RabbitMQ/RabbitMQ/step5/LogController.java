package RabbitMQ.RabbitMQ.step5;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/logs")
public class LogController {

    private final CustomExceptionHandler customExceptionHandler;

    @GetMapping("/error")
    public ResponseEntity<String> errorAPI(){
        try{
            String value = null;
            value.getBytes(); //null pointer
        }catch (Exception e){
            customExceptionHandler.handleException(e);
        }
        return ResponseEntity.ok().body("nullpointer Exception 처리");
    }

    @GetMapping("/warn")
    public ResponseEntity<String> warnAPI(){
        try{
            throw new IllegalAccessException("invalid argument입니다.");
        } catch (IllegalAccessException e) {
            customExceptionHandler.handleException(e);
        }
        return ResponseEntity.ok().body("IllegalArgument Exception 처리");
    }

    @PostMapping("/info")
    public ResponseEntity<String> infoAPI(@RequestBody String message){
        customExceptionHandler.handleMessage(message);
        return ResponseEntity.ok().body("info log 발송 처리");
    }
}
