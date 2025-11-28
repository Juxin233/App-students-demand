package fr.insa.ms.orchestraterService.model;

import java.util.List;

public class StudentView {
	private String prenom;
	private String nom;
	private String email;
	private String etablissement;
	private int annee;
	private List<String> capacites;
	private String filiere;
	private String avisUrl;
	
	public StudentView() {
		super();
	}

	public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEtablissement() {
		return etablissement;
	}

	public void setEtablissement(String etablissement) {
		this.etablissement = etablissement;
	}

	public int getAnnee() {
		return annee;
	}

	public void setAnnee(int annee) {
		this.annee = annee;
	}

	public List<String> getCapacites() {
		return capacites;
	}

	public void setCapacites(List<String> capacites) {
		this.capacites = capacites;
	}

	public String getFiliere() {
		return filiere;
	}

	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}

	public String getAvisUrl() {
		return avisUrl;
	}

	public void setAvisUrl(String avisUrl) {
		this.avisUrl = avisUrl;
	}
	
}
