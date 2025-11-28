package fr.insa.ms.recommendationService.service;

import fr.insa.ms.recommendationService.model.RecommendationRequest;
import fr.insa.ms.recommendationService.model.Student;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecommendationService {

    private final RestTemplate rest;

    public RecommendationService(RestTemplate rest){
        this.rest = rest;
    }

    public List<Integer> recommend(RecommendationRequest req){

        // 1) from StudentService 
        Student[] arr = rest.getForObject(
                "http://StudentService/students/summaries",
                Student[].class
        );
        
        List<Student> students = (arr == null) ? List.of() : Arrays.asList(arr);
        System.out.println("got all students.");
        // 2) rating classment
        return students.stream()
            .map(s -> Map.entry(s.getId(), score(req, s)))
            .sorted((a,b) -> Double.compare(b.getValue(), a.getValue()))
            .limit(5)
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());
    }

    // a function to map the request to caps
    private double score(RecommendationRequest req, Student s){
        double score = 0;

        List<String> mots = req.getMotsCles() == null ? List.of() : req.getMotsCles();
        List<String> caps = s.getCapacites() == null ? List.of() : s.getCapacites();
        System.out.println(mots.getFirst());
        System.out.println(caps.getFirst());
        // hits（motsCles ∩ capacites）
        long hits = mots.stream()
                .filter(m -> caps.stream().anyMatch(c -> c.equalsIgnoreCase(m)))
                .count();
        score += 3 * hits;

        // grade score
        if(s.getAnnee() >= 4) score += 1;
        System.out.println(score);
        return score;
    }
}
