package fr.insa.ms.studentService.model;

public class Capacite {
	private int id;
    private String capacite;

    public Capacite() {}

    public Capacite(int id, String capacite) {
        this.id = id;
        this.capacite = capacite;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getCapacite() { return capacite; }
    public void setCapacite(String capacite) { this.capacite = capacite; }

}
