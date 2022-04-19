package org.insa.graphs.gui.simple;

import java.awt.BorderLayout;
import org.insa.graphs.algorithm.*;
import java.awt.Dimension;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.gui.drawing.Drawing;
import org.insa.graphs.gui.drawing.components.BasicDrawing;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import java.lang.Double.*;

import org.insa.graphs.algorithm.shortestpath.*;
public class Launch {

    /**
     * Create a new Drawing inside a JFrame an return it.
     * 
     * @return The created drawing.
     * 
     * @throws Exception if something wrong happens when creating the graph.
     */
    public static Drawing createDrawing() throws Exception {
        BasicDrawing basicDrawing = new BasicDrawing();
        SwingUtilities.invokeAndWait(new Runnable() {
            @Override
            public void run() {
                JFrame frame = new JFrame("BE Graphes - Launch");
                frame.setLayout(new BorderLayout());
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setVisible(true);
                frame.setSize(new Dimension(800, 600));
                frame.setContentPane(basicDrawing);
                frame.validate();
            }
        });
        return basicDrawing;
    }

    public static void main(String[] args) throws Exception {

        // Visit these directory to see the list of available files on Commetud.
    	// il suffit de changer les maps pour avoir des tests sur des maps différentes 
        final String mapName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Maps/insa.mapgr";
        final String pathName = "/mnt/commetud/3eme Annee MIC/Graphes-et-Algorithmes/Paths/path_fr31insa_rangueil_r2.path";

        // Create a graph reader.
        final GraphReader reader = new BinaryGraphReader(
                new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

        // TODO: Read the graph.
        final Graph graph = reader.read();
        
        
        //on peut choisir les nodes ainsi que les Arcinspector etc pour le mode de trajet 
        ShortestPathData SPD = new ShortestPathData(graph, graph.get(283), graph.get(127), ArcInspectorFactory.getAllFilters().get(4));
        
        BellmanFordAlgorithm bellman= new BellmanFordAlgorithm(SPD);
        
        ShortestPathSolution result_b = bellman.run();
        
        DijkstraAlgorithm dijkstra= new DijkstraAlgorithm(SPD);
        
        ShortestPathSolution result_d = dijkstra.run();
        
        AStarAlgorithm astarf = new AStarAlgorithm(SPD);
        
        ShortestPathSolution result_a = astarf.run();
        
        if ((result_b.isFeasible() && result_d.isFeasible()) || (!result_b.isFeasible() && !result_d.isFeasible())) {
        	System.out.println("La faisibilité est bonne ! (deux infaisables ou deux faisables)");
        	if (result_b.isFeasible() && result_d.isFeasible()) {
	        	if (result_b.getPath().getMinimumTravelTime()==(result_d.getPath().getMinimumTravelTime())) {
	            	//c good 
	        		System.out.println(result_d);
	        		System.out.println(result_d.getPath().getMinimumTravelTime());
	            	System.out.println("Tout est bon ! ");
	            }
	            else {
	            	System.out.println("Mince alors ça ne fonctionne pas alors que la faisbilité était la bonne (erreur de notre programme)");
	            }
        	}
        	else  {
        		System.out.println("Car les 2 ne sont pas faisables");
        	}
        }
        else {
        	System.out.println("Flûte ! Les programmes ne sont pas d'accord sur la faisibilité");
        }
        
        if ((result_b.isFeasible() && result_a.isFeasible()) || (!result_b.isFeasible() && !result_a.isFeasible())) {
        	System.out.println("La faisibilité est bonne désormais");
        	if (result_b.isFeasible() && result_a.isFeasible()) {
	        	if (result_b.getPath().getMinimumTravelTime()==(result_a.getPath().getMinimumTravelTime())) {
	            	//c good 
	        		System.out.println(result_a);
	        		System.out.println(result_a.getPath().getMinimumTravelTime());
	            	System.out.println("Tout est bon ! ");
	            }
	            else {
	            	System.out.println("Mince alors ça ne fonctionne pas alors que la faisbilité était la bonne (erreur de notre programme)");
	            }
        	}
        	else  {
        		System.out.println("Car les 2 ne sont pas faisables");
        	}
        }
        else {
        	System.out.println("Flûte ! Les programmes ne sont pas d'accord sur la faisibilité");
        }
        
        // Create the drawing:
        //final Drawing drawing = createDrawing();

        // TODO: Draw the graph on the drawing.
        //drawing.drawGraph(graph);

        // TODO: Create a PathReader.
        //final PathReader pathReader = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(pathName))));

        // TODO: Read the path.
        //final Path path = pathReader.readPath(graph);

        // TODO: Draw the path.
    }

}
