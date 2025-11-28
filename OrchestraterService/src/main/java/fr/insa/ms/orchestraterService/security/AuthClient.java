package fr.insa.ms.orchestraterService.security;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class AuthClient {

    private final RestTemplate rest;

    public AuthClient(RestTemplate rest){
        this.rest = rest;
    }

    // verify and return studentId
    public Integer verify(String token){
        try{
            return rest.getForObject(
                "http://StudentService/auth/verify?token={token}",
                Integer.class,
                token
            );
        }catch(Exception e){
            return null;
        }
    }
}
