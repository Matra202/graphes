package org.insa.graphs.algorithm.shortestpath;
import  org.insa.graphs.model.Arc;

public class LabelStar extends Label {
	private double costDest;
	
	public LabelStar(int sommet_courant, boolean marque, double cout, Arc pere,double cd) {
		super(sommet_courant, marque, cout, pere);
		this.costDest=cd;
	}
	
	public double getcostDest() {
		return this.costDest;
	}
	
	public void setcostDest(double c) {
		this.costDest=c;
	}
	
	@Override
	public double getTotalCost(){
		return getCost() + getcostDest();
	}
}