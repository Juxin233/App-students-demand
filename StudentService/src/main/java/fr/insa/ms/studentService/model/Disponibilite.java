package fr.insa.ms.studentService.model;

import java.time.DayOfWeek;
import java.time.LocalTime;

public class Disponibilite {
	private DayOfWeek jour;
	private LocalTime heureDebut;
	private LocalTime heureFin;
	public Disponibilite(DayOfWeek jour, LocalTime heureDebut, LocalTime heureFin) {
		super();
		this.jour = jour;
		this.heureDebut = heureDebut;
		this.heureFin = heureFin;
	}
	public DayOfWeek getJour() {
		return jour;
	}
	public void setJour(DayOfWeek jour) {
		this.jour = jour;
	}
	public LocalTime getHeureDebut() {
		return heureDebut;
	}
	public void setHeureDebut(LocalTime heureDebut) {
		this.heureDebut = heureDebut;
	}
	public LocalTime getHeureFin() {
		return heureFin;
	}
	public void setHeureFin(LocalTime heureFin) {
		this.heureFin = heureFin;
	}
	
	
}
