package security.OAuth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import security.OAuth.Entity.Role;
import security.OAuth.Entity.User;
import security.OAuth.dto.UserForm;
import security.OAuth.repository.UserRepository;

@Controller
@Slf4j
@RequiredArgsConstructor
public class IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public String index(){
        return "index";
    }

    @GetMapping("/user") @ResponseBody
    public String user(){
        return "user";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    public String manager(){
        return "manager";
    }

    //config파일 작성 이후에는 기본 로그인페이지 작동 x
    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(UserForm userForm){
        log.info(userForm.getUsername());
        User user = new User(userForm);
        user.assignRole(Role.USER);
        user.bcryptPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user); //회원가입 비밀번호: 1234 => 시큐리티 로그인 불가능. why? 패스워드가 암호화가 안되었기 때문에
        return "redirect:/loginForm";
    }

}
