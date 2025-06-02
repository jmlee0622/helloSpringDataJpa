package kr.ac.hansung.cse.hellospringdatajpa.controller;


import jakarta.servlet.http.HttpSession;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String handleLogin(@RequestParam String email,@RequestParam String password,HttpSession session, Model model) {
        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent() && optionalUser.get().getPassword().equals(password)) {
            session.setAttribute("user", optionalUser.get()); // 로그인 성공
            return "redirect:/products"; // 홈으로 이동
        } else {
            model.addAttribute("error", "이메일 또는 비밀번호가 잘못되었습니다.");
            return "login";
        }
    }
}
