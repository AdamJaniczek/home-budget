package pl.com.itsystems.homeBudget;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;


@Controller
public class TransactionController {
    TransactionRepository transactionRepository;

    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("transaction", new Transaction());
        return "add";
    }

    @PostMapping("/add")
    public String add(Transaction transaction) {
        boolean result = transactionRepository.insert(transaction);
        if (result) {
            return "redirect:/add-status";
        } else {
            return "redirect:/";
        }
    }

    @GetMapping("/add-status")
    public String addStatus() {
        return "add-status";
    }


    @GetMapping("/filter")
    public String filter(TransactionType type, Model model) {
        List<Transaction> transactions = transactionRepository.findByType(type);
        model.addAttribute("transactions", transactions);
        model.addAttribute("type", type);
        return "filter";
    }


    @PostMapping("/remove")
    public String remove(@RequestParam long id, HttpSession session) {
        boolean result = transactionRepository.remove(id);
        session.setAttribute("removed", result);
        return "redirect:/delete-status";
    }

    @GetMapping("/delete-status")
    public String deleteSuccess(HttpSession session, Model model) {
        boolean result = (boolean) session.getAttribute("removed");
        model.addAttribute("removed", result);
        return "delete-status";
    }

}
