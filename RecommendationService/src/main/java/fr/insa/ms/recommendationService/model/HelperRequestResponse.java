package fr.insa.ms.recommendationService.model;

import java.util.List;

public class HelperRequestResponse {
    private String statut;
    private List<Student> recommendedHelpers;

    public HelperRequestResponse(){}

    public HelperRequestResponse(int demandId, String statut, List<Student> recommendedHelpers){
        this.statut = statut;
        this.recommendedHelpers = recommendedHelpers;
    }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public List<Student> getRecommendedHelpers() { return recommendedHelpers; }
    public void setRecommendedHelpers(List<Student> recommendedHelpers) { this.recommendedHelpers = recommendedHelpers; }
}

