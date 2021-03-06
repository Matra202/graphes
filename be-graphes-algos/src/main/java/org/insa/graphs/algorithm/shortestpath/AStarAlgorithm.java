package org.insa.graphs.algorithm.shortestpath;
import java.util.List;

import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
    }
    
    @Override 
    protected LabelStar[] create_label_list(ShortestPathData data) {
    	Graph graph = data.getGraph();

        final int nbNodes = graph.size();
        List<Node> nodes= graph.getNodes();
        // Initialize array of distances.
        Node finalNode=data.getDestination();
    	LabelStar[]labels = new LabelStar[nbNodes];
        double heuristiq;
        for (int i =0;i<nbNodes;i++) {
        	heuristiq=nodes.get(i).getPoint().distanceTo(finalNode.getPoint());
        	if(data.getMode()==AbstractInputData.Mode.TIME) {
        		heuristiq=heuristiq*3.6/((double) graph.getGraphInformation().getMaximumSpeed());
        	}
        	
        	labels[i] = new LabelStar(i, false, Double.POSITIVE_INFINITY, null,heuristiq);
        }
        return labels;
    }
}
