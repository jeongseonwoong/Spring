package hello.springmvc.basic.requestmapping;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class MappingClassController {

    @GetMapping
    public String user(){
        return "get users";
    }

    @PostMapping
    public String addUser(){
        return "post user";
    }

    @GetMapping("{userId}")
    public String findUser(@PathVariable String userId){
        return "get user";
    }

    @PatchMapping("{userId}")
    public String updateUser(@PathVariable String userId){
        return "patch user";
    }

    @DeleteMapping("{userId}")
    public String deleteUser(@PathVariable String userId){
        return "delete user";
    }

}

