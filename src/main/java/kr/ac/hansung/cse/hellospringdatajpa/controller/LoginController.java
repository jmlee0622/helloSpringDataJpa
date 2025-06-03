package kr.ac.hansung.cse.hellospringdatajpa.controller;


import jakarta.servlet.http.HttpSession;
import kr.ac.hansung.cse.hellospringdatajpa.entity.User;
import kr.ac.hansung.cse.hellospringdatajpa.repo.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
    @PostMapping("/logincheck")
    public String handleLogin(@RequestParam String email,
                              @RequestParam String password,
                              HttpSession session,
                              RedirectAttributes redirectAttributes,
                              Model model) {

        System.out.println("로그인 시도 - 이메일: " + email);

        Optional<User> optionalUser = userRepository.findByEmail(email);

        if (optionalUser.isPresent()) {
            String encodedPassword = optionalUser.get().getPassword();
            System.out.println("DB에 저장된 암호화된 비밀번호: " + encodedPassword);

            if (passwordEncoder.matches(password, encodedPassword)) {
                System.out.println("비밀번호 일치 - 로그인 성공");
                session.setAttribute("user", optionalUser.get());
                redirectAttributes.addFlashAttribute("success", "로그인 성공했습니다!");
                System.out.println("사용자 권한(role): " + optionalUser.get().getRole());  // role 출력
                return "redirect:/products";
            } else {
                System.out.println("비밀번호 불일치 - 로그인 실패");
            }
        } else {
            System.out.println("해당 이메일로 가입된 유저 없음");
        }

        model.addAttribute("error", "이메일 또는 비밀번호가 잘못");
        return "login";
    }

}
