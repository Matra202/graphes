package org.insa.graphs.algorithm.shortestpath;
import  org.insa.graphs.model.Arc;

public class Label implements Comparable<Label> {
	private int sommet_courant;
	private boolean marque;
	private double cout;
	private Arc pere;
	
	public Label(int sommet_courant, boolean marque, double cout, Arc pere) {
		this.sommet_courant=sommet_courant;
		this.marque=marque;
		this.cout=cout;
		this.pere=pere;
	}
	public double getCost() {
		return this.cout;
	}
	
	public int getSommet() {
		return this.sommet_courant;
	}

	public boolean getMarque() {
		return this.marque;
	}
	public Arc getPadre() {
		return this.pere;
	}
	
	
	public void setPadre(Arc p) {
		this.pere=p;
	}
	
	public void setMarque(boolean m) {
		this.marque=m;
	}
	
	public void setCout(double c) {
		this.cout=c;
	}
	
	public double getTotalCost() {
		return getCost();
	}
	@Override
    public int compareTo(Label other) {
        return Double.compare(getTotalCost(), other.getTotalCost());
    }
}
