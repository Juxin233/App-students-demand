package fr.insa.ms.recommendationService.model;

import java.util.List;

public class Student {
	private int id;
	private String passwd;
	private String prenom;
	private String nom;
	private String email;
	private String etablissement;
	private int annee;
	private List<String> capacites;
	private String filiere;
//	private List<Disponibilite> disponibilities;
//	private List<Avis> avisRecus;
	public Student() {}

	public void setId(int id) {
		this.id = id;
	}
	
	public int getId() {
		return id;
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
	public List<String> getCapability() {
		return capacites;
	}
	public void setCapability(List<String> capability) {
		this.capacites = capability;
	}
	public String getFiliere() {
		return filiere;
	}
	public void setFiliere(String filiere) {
		this.filiere = filiere;
	}
//	public List<Disponibilite> getDisponibilities() {
//		return disponibilities;
//	}
//	public void setDisponibilities(List<Disponibilite> disponibilities) {
//		this.disponibilities = disponibilities;
//	}
//	public List<Avis> getAvisRecus() {
//		return avisRecus;
//	}
//	public void setAvisRecus(List<Avis> avisRecus) {
//		this.avisRecus = avisRecus;
//	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	
}
