/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ClientProcess;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import static java.lang.Thread.sleep;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 *
 * @author CHUWI
 */
public class GraphDraw extends JFrame {

    int width;
    int height;

    ArrayList<Node> nodes;
    ArrayList<edge> edges;

    public GraphDraw() { //Constructor
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();

        /*this.addKeyListener(new KeyListener() {

            public void stateChanged(ChangeEvent e) {
                GraphDraw.this.repaint();
            }

            @Override
            public void keyTyped(KeyEvent e) {
                GraphDraw.this.repaint();
            }

            @Override
            public void keyPressed(KeyEvent e) {
                GraphDraw.this.repaint();
            }

            @Override
            public void keyReleased(KeyEvent e) {
                GraphDraw.this.repaint();
            }

        });*/
        this.setIgnoreRepaint(true);
        // this.set
        width = 30;
        height = 30;
    }

    protected void paintComponent(Graphics g) {
     
    }

    public GraphDraw(String name) { //Construct with label
        this.setTitle(name);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setIgnoreRepaint(true);
        nodes = new ArrayList<Node>();
        edges = new ArrayList<edge>();
        width = 30;
        height = 30;
    }

    class Node {

        int x, y;
        String name;

        public Node(String myName, int myX, int myY) {
            x = myX;
            y = myY;
            name = myName;
        }
    }

    class edge {

        int i, j;

        public edge(int ii, int jj) {
            i = ii;
            j = jj;
        }
    }

    public void addNode(String name, int x, int y) {
        //add a node at pixel (x,y)
        nodes.add(new Node(name, x, y));
        this.repaint();
    }

    public void addEdge(int i, int j) {
        //add an edge between nodes i and j
        edges.add(new edge(i, j));
        this.repaint();
    }

    public void paint(Graphics g2d) { // draw the nodes and edges
        
           BufferedImage bufferedImage = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bufferedImage.createGraphics();
        //paint using g2d ...

        g.setBackground(Color.white);

        g.clearRect(0, 0, 800, 600);
        FontMetrics f = g.getFontMetrics();
        int nodeHeight = Math.max(height, f.getHeight());

        int i = 0;
        for (Node n : nodes) {
            if (i++ == 2) {
                n.x += 2;
            }
        }
        g.setColor(Color.black);
        for (edge e : edges) {
            g.drawLine(nodes.get(e.i).x, nodes.get(e.i).y,
                    nodes.get(e.j).x, nodes.get(e.j).y);
        }

        for (Node n : nodes) {

            int nodeWidth = Math.max(width, f.stringWidth(n.name) + width / 2);
            g.setColor(Color.white);
            g.fillOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2,
                    nodeWidth, nodeHeight);
            g.setColor(Color.black);
            g.drawOval(n.x - nodeWidth / 2, n.y - nodeHeight / 2,
                    nodeWidth, nodeHeight);

            g.drawString(n.name, n.x - f.stringWidth(n.name) / 2,
                    n.y + f.getHeight() / 2);
        }
        
        Graphics2D g2dComponent = (Graphics2D) g2d;
        g2dComponent.drawImage(bufferedImage, null, 0, 0);
    }

    public static void main(String[] args) {
        GraphDraw frame = new GraphDraw("Test Window");

        frame.setSize(800, 600);

        frame.setVisible(true);

        frame.addNode("a", 50, 50);
        frame.addNode("b", 100, 100);
        frame.addNode("longNode", 200, 200);

        frame.addNode("c", 50, 200);
        frame.addEdge(0, 1);
        frame.addEdge(1, 2);
        frame.addEdge(0, 3);
        //for(int i = 0; i < 20000; i++)
        //    frame.repaint(100);
        int i = 0;
        while (++i < 200000000) {

            try {
                sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(GraphDraw.class.getName()).log(Level.SEVERE, null, ex);
            }
            frame.repaint();
        }
    }
}

