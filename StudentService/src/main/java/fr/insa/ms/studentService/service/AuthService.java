package fr.insa.ms.studentService.service;

import fr.insa.ms.studentService.repository.AuthRepository;
import fr.insa.ms.studentService.repository.StudentRepository;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final StudentRepository studentRepo;
    private final AuthRepository authRepo;

    public AuthService(StudentRepository studentRepo, AuthRepository authRepo){
        this.studentRepo = studentRepo;
        this.authRepo = authRepo;
    }

    // login in success and return token
    public String login(int id, String passwd){
        
    	boolean ok = studentRepo.login(id, passwd);
        if(!ok) return null;
        return authRepo.createToken(id);
    }

    public Integer verify(String token){
    	System.out.println("Authtification en cours : "+token);
    	return authRepo.verifyToken(token);
    }

    public boolean logout(String token){
        return authRepo.deleteToken(token);
    }
}
