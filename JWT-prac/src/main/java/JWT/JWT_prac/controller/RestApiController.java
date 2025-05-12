package JWT.JWT_prac.controller;

import JWT.JWT_prac.dto.RegisterDTO;
import JWT.JWT_prac.entity.User;
import JWT.JWT_prac.enums.Role;
import JWT.JWT_prac.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping("/home")
    public String home(){
        return "<h1>home</h1>";
    }

    @PostMapping("/token")
    public String token(){
        return "<h1>token</h1>";
    }

    @PostMapping("/join")
    public String join(@RequestBody RegisterDTO registerDTO){
        User user = new User();
        user.createUser(registerDTO.getUsername(), bCryptPasswordEncoder.encode(registerDTO.getPassword()), Role.ROLE_USER);
        userRepository.save(user);
        return "회원가입 완료";
    }

    @GetMapping("/api/v1/user")
    public String user(){
        return "user";
    }

    @GetMapping("/api/v1/manager")
    public String manager(){
        return "manager";
    }

    @GetMapping("/api/v1/admin")
    public String admin(){
        return "admin";
    }
}
