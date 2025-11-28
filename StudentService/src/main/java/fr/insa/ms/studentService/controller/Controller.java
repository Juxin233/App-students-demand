package fr.insa.ms.studentService.controller;

import org.springframework.web.bind.annotation.*;
import fr.insa.ms.studentService.service.StudentService;
import fr.insa.ms.studentService.model.Student;

import java.util.List;

@RestController
@RequestMapping("/students")
public class Controller {

    private final StudentService service;

    public Controller(StudentService service){
        this.service = service;
    }

    @PostMapping
    public int register(@RequestBody Student student){
        return service.register(student);
    }

    @PostMapping("/login")
    public boolean login(@RequestParam int id, @RequestParam String passwd){
        return service.login(id, passwd);
    }

    @PutMapping("/update")
    public boolean update(@RequestBody Student student){
        return service.update(student);
    }

    @GetMapping
    public List<Student> all(){
        return service.all();
    }

    // for Recommendation 
    @GetMapping("/summaries")
    public List<Student> summaries(){
        return service.all();
    }

    // for Orchestrater 
    @PostMapping("/batch")
    public List<Student> batch(@RequestBody List<Integer> ids){
        return service.batch(ids);
    }
    
    
}
