package pl.com.itsystems.homeBudget;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class HomeController {
    private TransactionRepository repository;

    public HomeController(TransactionRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public String HomeController(Model model) {
        List<Transaction> transactions = repository.findAll();
        model.addAttribute("transactions", transactions);
        return "home";
    }
}
