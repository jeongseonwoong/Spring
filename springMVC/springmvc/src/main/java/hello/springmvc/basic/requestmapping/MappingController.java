package hello.springmvc.basic.requestmapping;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
public class MappingController {

    private Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping({"/hello-basic","/hello-go"})
    public String helloBasic(){
        log.info("hello-basic");
        return "ok";
    }

    /**
     * pathVariable 사용
     * 변수명이 같으면 생략 가능
     * @PathVariable("userId") String userId ->@PathVariable userId
     */
    @GetMapping("/mapping/{userId}")//url 자체에 변수가 있음
    public String mappingPath(@PathVariable("userId") String data){
        log.info("userId={}",data);
        return "ok";
    }

    @GetMapping(value = "/mapping/{userId}", params = "mode!=debug")
    public String mappingDebug(@PathVariable("userId") String data){
        log.info("userId={}",data);
        return "really ok";
    }

    @GetMapping(value ="/mapping-headers",headers = "mode=debug")
    public String mappingHeaders(){
        log.info("headers");
        return "ok";
    }

    @PostMapping(value="/mapping-consume", consumes = MediaType.APPLICATION_JSON_VALUE)
    public String mappingConsume(@RequestBody String data){
        log.info("data={}",data);
        return "ok";
    }

    @GetMapping(value = "/mapping-produce", produces="text/html")
    public String mappingProduce(){
        log.info("produce");
        return "/index";
    }
}
