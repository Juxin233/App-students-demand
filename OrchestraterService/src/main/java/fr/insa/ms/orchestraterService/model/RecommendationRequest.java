package fr.insa.ms.orchestraterService.model;

import java.util.List;

public class RecommendationRequest {
//    private int demandId;
//    private String description;
    private List<String> motsCles;

    public RecommendationRequest(){}

    public RecommendationRequest(List<String> motsCles){
//        this.demandId = demandId;
//        this.description = description;
        this.motsCles = motsCles;
    }

//    public int getDemandId() { return demandId; }
//    public void setDemandId(int demandId) { this.demandId = demandId; }

//    public String getDescription() { return description; }
//    public void setDescription(String description) { this.description = description; }

    public List<String> getMotsCles() { return motsCles; }
    public void setMotsCles(List<String> motsCles) { this.motsCles = motsCles; }
}




