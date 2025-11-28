package fr.insa.ms.recommendationService.controller;

import fr.insa.ms.recommendationService.model.RecommendationRequest;
import fr.insa.ms.recommendationService.service.RecommendationService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recommendations")
public class Controller {

    private final RecommendationService service;

    public Controller(RecommendationService service){
        this.service = service;
    }

    @PostMapping
    public List<Integer> recommend(@RequestBody RecommendationRequest req){
        System.out.println("recommend post called.");
    	return service.recommend(req);
    }
}



