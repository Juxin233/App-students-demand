package fr.insa.ms.recommendationService.model;

import java.util.List;

public class RecommendationRequest {
    private List<String> motsCles;

    public RecommendationRequest(){}

    public RecommendationRequest(List<String> motsCles){
        this.motsCles = motsCles;
    }

    public List<String> getMotsCles() { return motsCles; }
    public void setMotsCles(List<String> motsCles) { this.motsCles = motsCles; }
}




