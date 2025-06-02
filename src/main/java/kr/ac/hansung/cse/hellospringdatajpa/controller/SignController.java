package kr.ac.hansung.cse.hellospringdatajpa.controller;


import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import org.springframework.ui.Model;

@Controller
public class SignController {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public SignController(UserRepository userRepository,PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder=passwordEncoder;
    }

    @GetMapping("/sign")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "sign";
    }

    @PostMapping("/sign")
    public String handleSignUp(@ModelAttribute User user, Model model) {
        String rawPassword = user.getPassword();
        String encodedPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encodedPassword);
        userRepository.save(user);
        return "redirect:/login";
    }
}
