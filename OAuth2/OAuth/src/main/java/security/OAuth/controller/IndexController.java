package security.OAuth.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import security.OAuth.Entity.Role;
import security.OAuth.Entity.User;
import security.OAuth.config.auth.CustomUserDetails;
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
    @ResponseBody
    public String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
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
        user.assignRole(Role.ROLE_USER);
        user.bcryptPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user); //회원가입 비밀번호: 1234 => 시큐리티 로그인 불가능. why? 패스워드가 암호화가 안되었기 때문에
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info") @ResponseBody
    public String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/data") @ResponseBody
    public String data(){
        return "데이터";
    }

    @GetMapping("/test/login") @ResponseBody
    public String testLogin(Authentication authentication, @AuthenticationPrincipal UserDetails userDetails2){
        //Authentication 객체 안에 Principal이 있고 Principal을 CustomUserDetails로 형변환하여 user 정보 가져올 수 있음
        log.info("================/test/login==================");
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        log.info("=================Authentication==================");
        log.info("authentication: {}", userDetails.getUser());

        log.info("=================@Authentication==================");
        log.info("userDetails: {}", userDetails2.getUsername());
        return "세션 정보 확인";
    }
}
