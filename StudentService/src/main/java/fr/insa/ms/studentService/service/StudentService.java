package fr.insa.ms.studentService.service;

import fr.insa.ms.studentService.model.Student;
import fr.insa.ms.studentService.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository repo;

    public StudentService(StudentRepository repo){
        this.repo = repo;
    }

    public int register(Student s){
        return repo.register(s);
    }

    public boolean login(int id, String passwd){
        return repo.login(id, passwd);
    }

    public boolean update(Student s){
        return repo.update(s);
    }

    public List<Student> all(){
        return repo.all();
    }

    public List<Student> batch(List<Integer> ids){
        return repo.batch(ids);
    }
}
