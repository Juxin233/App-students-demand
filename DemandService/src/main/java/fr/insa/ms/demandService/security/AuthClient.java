package fr.insa.ms.demandService.security;

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
        	System.out.println("Authentification en cours");
            int res= rest.getForObject(
                "http://StudentService/auth/verify?token={token}",
                Integer.class,
                token
            );
            System.out.println("Authentification fini id*"+res);
            return res;
        }catch(Exception e){
            return null;
        }
    }
}
