import org.graphstream.algorithm.coloring.WelshPowell;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.GridGenerator;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

public class BathroomTest {

    static int NUM_TILES = 6;

    public static void main(String[] args) throws IOException, ElementNotFoundException, GraphParseException {
        Graph graph = new SingleGraph("My bathroom");

        Generator gen = new GridGenerator(false, false);
        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < NUM_TILES; i++) {
            gen.nextEvents();
        }
        gen.end();

        WelshPowell wp = new WelshPowell("color");
        wp.init(graph);
        wp.compute();

        // debug
        System.out.println("The chromatic number of this graph is : " + wp.getChromaticNumber());

        HashMap<Integer, Integer> tiles = new HashMap<Integer, Integer>();
        for (Node n : graph) {
            Integer myColor = n.getAttribute("color");
            System.out.println("Node " + n.getId() + " : color " + myColor);
            int count = tiles.containsKey(myColor) ? tiles.get(myColor) : 0;
            tiles.put(myColor, count + 1);
        }

        Iterator<Integer> keySetIterator = tiles.keySet().iterator();
        while (keySetIterator.hasNext()) {
            Integer key = keySetIterator.next();
            System.out.println("Tiles: " + key + " count: " + tiles.get(key));
        }

        Color[] cols = new Color[wp.getChromaticNumber()];
        cols[0] = Color.MAGENTA;
        cols[1] = Color.LIGHT_GRAY;
        cols[2] = Color.PINK;
        cols[3] = Color.blue;
        // extra colors
//        cols[4] = Color.YELLOW;

        for (Node n : graph) {
            int col = (int) n.getNumber("color");
            n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
        }

        graph.display();
    }
}