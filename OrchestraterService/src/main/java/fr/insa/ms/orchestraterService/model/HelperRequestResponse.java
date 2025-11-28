package fr.insa.ms.orchestraterService.model;

import java.util.List;

public class HelperRequestResponse {
    private String statut;
    private List<StudentView> recommendedHelpers;

    public HelperRequestResponse(){}

    public HelperRequestResponse(String statut, List<StudentView> recommendedHelpers){
        this.statut = statut;
        this.recommendedHelpers = recommendedHelpers;
    }

    public String getStatut() { return statut; }
    public void setStatut(String statut) { this.statut = statut; }

    public List<StudentView> getRecommendedHelpers() { return recommendedHelpers; }
    public void setRecommendedHelpers(List<StudentView> recommendedHelpers) { this.recommendedHelpers = recommendedHelpers; }
}

