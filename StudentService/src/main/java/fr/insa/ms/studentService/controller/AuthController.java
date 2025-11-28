package fr.insa.ms.studentService.controller;

import fr.insa.ms.studentService.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService){
        this.authService = authService;
    }

    // login in and return token
    @PostMapping("/login")
    public String login(@RequestParam int id, @RequestParam String passwd){
        String token = authService.login(id, passwd);
        return token == null ? "LOGIN_FAILED" : token;
    }

    // verify token and return studentId
    @GetMapping("/verify")
    public Integer verify(@RequestParam String token){
        Integer id = authService.verify(token);
        if(id == null) throw new RuntimeException("INVALID_TOKEN");
        return id;
    }

    @DeleteMapping("/logout")
    public boolean logout(@RequestParam String token){
        return authService.logout(token);
    }
}
