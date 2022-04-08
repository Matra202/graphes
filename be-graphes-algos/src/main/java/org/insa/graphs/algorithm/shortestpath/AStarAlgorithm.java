package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
        // TODO:
        final int nbNodes = graph.size();
        List<Node> nodes= graph.getNodes();
        // Initialize array of distances.
        Node finalNode=data.getDestination();
        LabelStar[]labels = new LabelStar[nbNodes];
        double heuristiq;
        for (int i =0;i<nbNodes;i++) {
        	heuristiq=nodes.get(i).getPoint().distanceTo(finalNode.getPoint());
        	labels[i] = new LabelStar(i, false, Double.POSITIVE_INFINITY, null,heuristiq);
        }
        BinaryHeap<Label> Tas = new BinaryHeap<>();
        labels[data.getOrigin().getId()].setCout(0);
        Tas.insert(labels[data.getOrigin().getId()]);
        // Actual algorithm, we will assume the graph does not contain negative
        // cycle...
        //int compteur_sommet_marques = 0;
        boolean noussommesarrives = false;
        while (!Tas.isEmpty()) {
        	Label minlab = Tas.deleteMin();
        	minlab.setMarque(true);
        	notifyNodeReached(nodes.get(minlab.getSommet()));
        	noussommesarrives=nodes.get(minlab.getSommet())==finalNode;
        	//compteur_sommet_marques++;
        	if (noussommesarrives) {
        		break;
        	}
            for (Arc arc: nodes.get(minlab.getSommet()).getSuccessors()) {
            // Small test to check allowed roads...
                 if (!data.isAllowed(arc)) {
                     continue;
                 }
                 if (!(labels[arc.getDestination().getId()].getMarque())) {
                	 //calcul du min
                	 double mincost = Double.min(labels[arc.getDestination().getId()].getCost(),
                			 minlab.getCost() + arc.getLength());
                	 boolean changed = mincost != labels[arc.getDestination().getId()].getCost();
                	 
                	 if (changed) {
                		 labels[arc.getDestination().getId()].setCout(mincost);
                		 if (Tas.search(labels[arc.getDestination().getId()],0)!=-1){
                			 Tas.remove(labels[arc.getDestination().getId()]); 
                		 }
                		 labels[arc.getDestination().getId()].setPadre(arc);
                		 Tas.insert(labels[arc.getDestination().getId()]);
                	 }
                 }
            }
        }
        if (!noussommesarrives) {
        	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	// The destination has been found, notify the observers.
            notifyDestinationReached(finalNode);

            // Create the path from the array of predecessors...
            ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels[finalNode.getId()].getPadre();
            while (arc != null) {
                arcs.add(arc);
                arc = labels[arc.getOrigin().getId()].getPadre();
            }

            // Reverse the path...
            Collections.reverse(arcs);

            // Create the final solution.
            System.out.println("MAIS C LE NOTRE PROUT");
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }

}
