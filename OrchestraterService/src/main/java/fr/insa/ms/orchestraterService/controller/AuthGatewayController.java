package fr.insa.ms.orchestraterService.controller;

import fr.insa.ms.orchestraterService.model.Student;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/auth")
public class AuthGatewayController {

    private final RestTemplate rest;

    public AuthGatewayController(RestTemplate rest){
        this.rest = rest;
    }

    // register：transmit to StudentService
    @PostMapping("/register")
    public boolean register(@RequestBody Student s){
        Boolean ok = rest.postForObject(
            "http://StudentService/students",
            s,
            Boolean.class
        );
        return ok != null && ok;
    }

    // longin in ：transmit to StudentService，return token
    @PostMapping("/login")
    public String login(@RequestParam int id, @RequestParam String passwd){
        String token = rest.postForObject(
            "http://StudentService/auth/login?id={id}&passwd={passwd}",
            null,
            String.class,
            id, passwd
        );
        return token == null ? "LOGIN_FAILED" : token;
    }

    // logout ：transmit it to StudentService
    @DeleteMapping("/logout")
    public boolean logout(@RequestParam String token){
        String url = "http://StudentService/auth/logout?token="+token;
        Boolean ok = rest.exchange(
            url,
            org.springframework.http.HttpMethod.DELETE,
            null,
            Boolean.class
        ).getBody();
        return ok != null && ok;
    }
}

