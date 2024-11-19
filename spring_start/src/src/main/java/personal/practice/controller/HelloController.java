package personal.practice.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {

    @GetMapping("hello")//파일 hello (버튼 이름 x)
    public String hello(Model model) {
        model.addAttribute("data", "헬로");
        return "hello";
    }

    @GetMapping("hello-template")
    public String helloString(Model model){
        model.addAttribute("data", "템플릿");
        return "hello";
    }

    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name")String name){
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }

    static class Hello{
        private String name;

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name=name;
        }



    }



}
