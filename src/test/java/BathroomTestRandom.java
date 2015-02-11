import org.graphstream.graph.Edge;
import org.graphstream.graph.ElementNotFoundException;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.stream.GraphParseException;

import java.awt.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;

public class BathroomTestRandom {

    static int HEIGHT = 9;
    static int WIDTH = 14;
    static int NUM_COLORS = 4;

    public static void main(String[] args) throws IOException, ElementNotFoundException, GraphParseException {
        Graph wall = new SingleGraph("My wall");
        Random rand = new Random();

        // number of tiles
        int numTiles = HEIGHT * WIDTH;

        // add tiles
        for (int i = 0; i < numTiles; i++) {
            String nodeName = String.valueOf(i);
            wall.addNode(nodeName);
        }

        // add edges
        for (int i = 0; i < numTiles - 1; i++) {
            String node0Name = String.valueOf(i);
            // horizontal edges
            String node1Name = String.valueOf(i + 1);
            if (((i + 1) % WIDTH) != 0) {
                String edgeName = node0Name + "_" + node1Name;
                wall.addEdge(edgeName, node0Name, node1Name);
            }
            // vertical edges
            String node2Name = String.valueOf(i + WIDTH);
            if (i + WIDTH < numTiles) {
                String edgeName = node0Name + "_" + node2Name;
                wall.addEdge(edgeName, node0Name, node2Name);
            }
        }

        // true random colors
        for (Node n : wall) {
            Integer color = rand.nextInt(NUM_COLORS);
            n.setAttribute("color", color);
        }

        // debug
        HashMap<Integer, Integer> tiles = new HashMap<Integer, Integer>();

        // print the color of each node
        for (Node n : wall) {
            Integer myColor = n.getAttribute("color");
            System.out.println("Node " + n.getId() + " : color " + myColor);
            int count = tiles.containsKey(myColor) ? tiles.get(myColor) : 0;
            tiles.put(myColor, count + 1);
        }

        // How many tiles for each color
        Iterator<Integer> keySetIterator = tiles.keySet().iterator();
        while (keySetIterator.hasNext()) {
            Integer key = keySetIterator.next();
            System.out.println("Tiles: " + key + " count: " + tiles.get(key));
        }

        Color[] cols = new Color[NUM_COLORS];
        cols[0] = Color.MAGENTA;
        cols[1] = Color.LIGHT_GRAY;
        cols[2] = Color.PINK;
        cols[3] = Color.BLUE;

        for (Node n : wall) {
            int col = (int) n.getNumber("color");
            n.addAttribute("ui.style", "fill-color:rgba(" + cols[col].getRed() + "," + cols[col].getGreen() + "," + cols[col].getBlue() + ",200);");
        }

        wall.addAttribute("ui.stylesheet", "node {shape:box;  size: 50px;}");
        wall.display();
    }
}