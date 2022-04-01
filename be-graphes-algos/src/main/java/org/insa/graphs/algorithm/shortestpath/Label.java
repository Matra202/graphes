package org.insa.graphs.algorithm.shortestpath;
import  org.insa.graphs.model.Arc;

public class Label {
	private int sommet_courant;
	private boolean marque;
	private double cout;
	private Arc pere;
	private int serial;
	
	public Label(int sommet_courant, boolean marque, double cout, Arc pere,int s) {
		this.sommet_courant=sommet_courant;
		this.marque=marque;
		this.cout=cout;
		this.pere=pere;
		this.serial=s;
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
	
	public int getSerial() {
		return this.serial;
	}
	
}
