package fr.insa.ms.orchestraterService.model;

import java.time.LocalDateTime;
import java.util.List;

public class Demand {

    private int id;
    private String titre;
    private String description;
    private List<String> motsCles;
    private int demandeurId;
    private Integer repondeurId;          
    private LocalDateTime datesouhaitee;
    private StatutDemande statut;
    private String avis;              

    public enum StatutDemande{
        En_attente,
        En_cours,
        Realisee,
        Abandonnee,
        Fermee
    }

    public Demand() {}

    // getter / setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getMotsCles() { return motsCles; }
    public void setMotsCles(List<String> motsCles) { this.motsCles = motsCles; }

    public int getDemandeurId() { return demandeurId; }
    public void setDemandeurId(int demandeurId) { this.demandeurId = demandeurId; }

    public Integer getRepondeurId() { return repondeurId; }
    public void setRepondeurId(Integer repondeurId) { this.repondeurId = repondeurId; }

    public LocalDateTime getDatesouhaitee() { return datesouhaitee; }
    public void setDatesouhaitee(LocalDateTime datesouhaitee) { this.datesouhaitee = datesouhaitee; }

    public StatutDemande getStatut() { return statut; }
    public void setStatut(StatutDemande statut) { this.statut = statut; }

    public String getAvis() { return avis; }
    public void setAvis(String avis) { this.avis = avis; }
}
