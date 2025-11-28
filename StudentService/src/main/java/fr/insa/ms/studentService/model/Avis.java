package fr.insa.ms.studentService.model;


public class Avis {
	private int id;
	private String titre;
	private String description;
	private int aidantId;
	private int demandeurId;
	private Double score;
	public Avis(int id, String titre, String description, int aidantId, int demandeurId, Double score) {
		super();
		this.id = id;
		this.titre = titre;
		this.description = description;
		this.aidantId = aidantId;
		this.demandeurId = demandeurId;
		this.score = score;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getTitre() {
		return titre;
	}
	public void setTitre(String titre) {
		this.titre = titre;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getAidantId() {
		return aidantId;
	}
	public void setAidantId(int aidantId) {
		this.aidantId = aidantId;
	}
	public int getDemandeurId() {
		return demandeurId;
	}
	public void setDemandeurId(int demandeurId) {
		this.demandeurId = demandeurId;
	}
	public Double getScore() {
		return score;
	}
	public void setScore(Double score) {
		this.score = score;
	}
}
