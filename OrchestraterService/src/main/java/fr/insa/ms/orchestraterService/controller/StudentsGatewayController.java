package fr.insa.ms.orchestraterService.controller;

import fr.insa.ms.orchestraterService.model.Student;
import fr.insa.ms.orchestraterService.security.AuthClient;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentsGatewayController {

    private final RestTemplate rest;
    private final AuthClient authClient;

    public StudentsGatewayController(RestTemplate rest, AuthClient authClient) {
        this.rest = rest;
        this.authClient = authClient;
    }

    // ---------- public search：transmit without authentification ----------

    @GetMapping("/summaries")
    public Student[] summaries() {
        return rest.getForObject(
                "http://StudentService/students/summaries",
                Student[].class
        );
    }

//    @GetMapping("/all")
//    public String[] allNames() {
//        return rest.getForObject(
//                "http://StudentService/students/all",
//                String[].class
//        );
//    }

//    @GetMapping("/{id}")
//    public Student get(@PathVariable int id) {
//        return rest.getForObject(
//                "http://StudentService/students/{id}",
//                Student.class,
//                id
//        );
//    }

    @PostMapping("/batch")
    public Student[] batch(@RequestBody List<Integer> ids) {
        return rest.postForObject(
                "http://StudentService/students/batch",
                ids,
                Student[].class
        );
    }

    // ---------- modification after authentification：valid token then transmit ----------

    @PutMapping("/update")
    public boolean update(@RequestBody Student s,
                          @RequestHeader(value="Authorization", required=false) String authHeader) {
    	System.out.println();
        Integer userId = extractAndVerify(authHeader);
        System.out.println(userId);
        if(userId == null) return false;
        s.setId(userId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Student> entity = new HttpEntity<>(s,headers);
        ResponseEntity<Boolean> resp = rest.exchange(
                "http://StudentService/students/update",
                HttpMethod.PUT,
                entity,
                Boolean.class
        );
        return Boolean.TRUE.equals(resp.getBody());
    }

    private Integer extractAndVerify(String authHeader){
        if(authHeader == null || !authHeader.startsWith("Bearer ")) return null;
        String token = authHeader.substring(7);
        return authClient.verify(token);
    }
}
