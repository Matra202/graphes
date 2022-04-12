package org.insa.graphs.algorithm.shortestpath;
import java.util.*;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.algorithm.utils.ElementNotFoundException;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    protected Label[] create_label_list(ShortestPathData data) {
    	int nbNodes= data.getGraph().size();
    	Label[]labels = new Label[nbNodes]; 
        for (int i =0;i<nbNodes;i++) {
        	labels[i] = new Label(i, false, Double.POSITIVE_INFINITY, null);
        }
        return labels;
    }
    
    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ShortestPathSolution solution = null;
        // TODO:
        List<Node> nodes= graph.getNodes();
        // Initialize array of distances.
        Node finalNode=data.getDestination();
        Label[]labels = create_label_list(data);
        BinaryHeap<Label> Tas = new BinaryHeap<>();
        labels[data.getOrigin().getId()].setCout(0);
        Tas.insert(labels[data.getOrigin().getId()]);
        // Actual algorithm, we will assume the graph does not contain negative
        // cycle...
        boolean noussommesarrives = false;
        while (!Tas.isEmpty()) {
        	Label minlab = Tas.deleteMin();
        	minlab.setMarque(true);
        	notifyNodeMarked(nodes.get(minlab.getSommet()));
        	noussommesarrives=nodes.get(minlab.getSommet())==finalNode;//compareto
        	if (noussommesarrives) {
        		break;
        	}
            for (Arc arc: nodes.get(minlab.getSommet()).getSuccessors()) {
            // Small test to check allowed roads...
            	if (!data.isAllowed(arc)) {
            		continue;
            	}
                 if (!(labels[arc.getDestination().getId()].getMarque())) {
                	 notifyNodeReached(arc.getDestination());
                	 //calcul du min
                	 double mincost = Double.min(labels[arc.getDestination().getId()].getCost(),
  			 					minlab.getCost() + data.getCost(arc));
                	 
                	 boolean changed = mincost < labels[arc.getDestination().getId()].getCost();//compareto
                	 
                	 if (changed) {
                		 
                		 if (!Double.isInfinite(labels[arc.getDestination().getId()].getCost())){
                			 try {
                				 Tas.remove(labels[arc.getDestination().getId()]);  
                			 }
                			 catch (ElementNotFoundException error){
                				 System.out.println("error");
                			 }
                		 }
                		 labels[arc.getDestination().getId()].setCout(mincost);
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
            solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
        }
        return solution;
    }

}
