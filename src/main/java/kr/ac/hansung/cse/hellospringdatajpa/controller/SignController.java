package kr.ac.hansung.cse.hellospringdatajpa.controller;


import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import org.springframework.ui.Model;

@Controller
public class SignController {
    private final UserRepository userRepository;

    @Autowired
    public SignController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/sign")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "sign";
    }

    @PostMapping("/sign")
    public String handleSignUp(@ModelAttribute User user, Model model) {
        userRepository.save(user);
        return "redirect:/products"; // 홈 또는 로그인 페이지로 리디렉션
    }
}
