import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.lowtech.webshop.service.EmailService;

@RestController
public class EmailControllerTest {

    @Autowired
    private EmailService emailService;

    @GetMapping("/send-email")
    public String sendTestEmail(@RequestParam String to, @RequestParam String subject, @RequestParam String body) {
        emailService.sendEmail(to, subject, body);
        return "Email sent successfully to " + to;
    }
}
