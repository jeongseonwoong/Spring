package RabbitMQ.RabbitMQ.step4;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("message","Welcome to RabbitMQ Sample!");
        return "home";
    }

    @GetMapping("/news")
    public String news(Model model){
        model.addAttribute("message","Welcome to RabbitMQ News Sample!");
        return "news";
    }
}
