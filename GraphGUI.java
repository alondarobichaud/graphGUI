import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**                                                                                                                       
 *                                                                                                                        
 * Implements a GUI for inputting nodes and edges                                                                         
 * @author Alonda Robichaud                                                                                               
 * @version CSC 212, December 10 2017                                                                                     
 *                                                                                                                        
 */

public class GraphGUI {
    /** The graph to be displayed */
    private GraphCanvas canvas;

    /** Label for the input mode instructions */
    private JLabel instr;

    /** The input mode */
    InputMode mode = InputMode.ADD_NODES;

    /** Remembers node where last mousepressed event occurred */
    Graph<NodeData,EdgeData>.Node nodePressed;
    /** Remembers node where last mousereleased event occurred */
    Graph<NodeData,EdgeData>.Node nodeReleased;
    /** Rembers point where mouse is */
    Point pointUnderMouse;

    /**                                                                                                                   
     * Schedules a job for the event-dispatching thread                                                                   
     * creating and showing this application's GUI.                                                                       
     */
    public static void main(String[] args) {
        final GraphGUI GUI = new GraphGUI();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
                public void run() {
                    GUI.createAndShowGUI();
                }
            });
    }
    
     /** Sets up the GUI window */
    public void createAndShowGUI() {
        // Make sure we have nice window decorations.                                                                     
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.                                                                                  
        JFrame frame = new JFrame("Graph GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add components                                                                                                 
        createComponents(frame);

        // Display the window.                                                                                            
        frame.pack();
        frame.setVisible(true);
    }

    /** Puts content in the GUI window */
    public void createComponents(JFrame frame) {
        // graph display                                                                                                  
        Container pane = frame.getContentPane();
        pane.setLayout(new FlowLayout());
        JPanel panel1 = new JPanel();
        panel1.setLayout(new BorderLayout());
        canvas = new GraphCanvas();
        NodeMouseListener pml = new NodeMouseListener();
        canvas.addMouseListener(pml);
        canvas.addMouseMotionListener(pml);
         panel1.add(canvas);
        instr = new JLabel("Click to add new nodes.");
        panel1.add(instr,BorderLayout.NORTH);
        pane.add(panel1);

        // controls/buttons                                                                                               
        JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayout(9,1));
        JButton addNodeButton = new JButton("Add/Move Nodes");
        panel2.add(addNodeButton);
        addNodeButton.addActionListener(new AddNodeListener());
        JButton rmvNodeButton = new JButton("Remove Nodes");
        panel2.add(rmvNodeButton);
        rmvNodeButton.addActionListener(new RmvNodeListener());
        JButton addEdgeButton = new JButton("Add Edges");
        panel2.add(addEdgeButton);
        addEdgeButton.addActionListener(new AddEdgeListener());
        JButton rmvEdgeButton = new JButton("Remove Edge");
        panel2.add(rmvEdgeButton);
        rmvEdgeButton.addActionListener(new RmvEdgeListener());
        JButton BFT = new JButton("Breadth-First Traversal");
        panel2.add(BFT);
        BFT.addActionListener(new bftListener());
        JButton DFT = new JButton("Depth-First Traversal");
         panel2.add(DFT);
        DFT.addActionListener(new dftListener());
        JButton dijkstra = new JButton("Dijkstra");
        panel2.add(dijkstra);
        dijkstra.addActionListener(new dijkstraListener());
        JButton reset = new JButton("Reset");
        panel2.add(reset);
        reset.addActionListener(new resetListener());
        JButton clear = new JButton("Clear");
        panel2.add(clear);
        clear.addActionListener(new clearListener());
        pane.add(panel2);

    }

    /**                                                                                                                   
     * Returns a Node found within the drawing radius of the given location,                                              
     * or null if none                                                                                                    
     *                                                                                                                    
     *  @param x  the x coordinate of the location                                                                        
     *  @param y  the y coordinate of the location                                                                        
     *  @return  a Node from the canvas, if there is one, covering this location,                                         
     *  or a null reference if not                                                                                        
     */
      public Graph<NodeData,EdgeData>.Node findNearbyNode(int x, int y) {
        Point xy = new Point(x,y);
        for (Graph<NodeData,EdgeData>.Node node : canvas.nodes){
            double distance = xy.distance(node.getData().getPoint());
            if (distance <= 10 && distance >= -10){
                return node;
            }
        }
        return null;
    }

    /** Constants for recording the input mode */
    enum InputMode {
        ADD_NODES, RMV_NODES, ADD_EDGES, RMV_EDGES, BFT, DFT, DIJKSTRA, RESET, CLEAR
    }

    /** Listener for AddNode button */
    private class AddNodeListener implements ActionListener {
        /** Event handler for Addnode button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.ADD_NODES;
            instr.setText("Click to add new nodes.");
        }
    }
    
     /** Listener for AddEdge button */
    private class AddEdgeListener implements ActionListener {
        /** Event handler for AddEdge button */
        public void actionPerformed(ActionEvent e){
            mode = InputMode.ADD_EDGES;
            instr.setText("Drag to add new edges.");
        }
    }

    /** Listener for Rmvnode button */
    private class RmvNodeListener implements ActionListener {
        /** Event handler for RmvNode button */
        public void actionPerformed(ActionEvent e) {
            mode = InputMode.RMV_NODES;
            instr.setText("Click to remove nodes.");
        }
    }

    /** Listener for RmvEdge button */
    private class RmvEdgeListener implements ActionListener {
        /** Event handler for RmvEdge button */
        public void actionPerformed(ActionEvent e){
            mode = InputMode.RMV_EDGES;
            instr.setText("Click to remove edges.");
         }
    }

    /** Listener for BFT button */
    private class bftListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            mode = InputMode.BFT;
            instr.setText("Click a node to start traversal");
        }
    }

    /** Listener for DFT button */
    private class dftListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            mode = InputMode.DFT;
            instr.setText("Click a node to start traversal");
        }
    }

    /** Listener for dijkstra button */
    private class dijkstraListener implements ActionListener {
        public void actionPerformed(ActionEvent e){
            mode = InputMode.DIJKSTRA;
            instr.setText("Click a node to start traversal");
        }
    }
    
     /** Listener for reset button */
    private class resetListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mode = InputMode.RESET;
            instr.setText("Graph reset");
            // resets all the edges and nodes to their original colors                                                    
            for (Graph<NodeData,EdgeData>.Edge edge : canvas.edges){
                edge.getData().setColor(Color.BLACK);
            }
            for (Graph<NodeData,EdgeData>.Node node : canvas.nodes){
                node.getData().setColor(Color.RED);
            }
            canvas.repaint();
        }
    }

    /** Listener for clear button */
    private class clearListener implements ActionListener{
        public void actionPerformed(ActionEvent e){
            mode = InputMode.CLEAR;
            instr.setText("Graph cleared");

            // removes all nodes and edges                                                                                
            while (!canvas.edges.isEmpty()){
                canvas.edges.removeLast();
            }
            while (!canvas.nodes.isEmpty()){
                canvas.nodes.removeLast();
            }
            canvas.repaint();
        }
    }

    /** Mouse listener for NodeCanvas element */
    private class NodeMouseListener extends MouseAdapter
        implements MouseMotionListener {

        /** Responds to click event depending on mode */
        public void mouseClicked(MouseEvent e) {
            Graph<NodeData,EdgeData>.Node clicked = findNearbyNode(e.getX(),e.getY());
            switch (mode) {
                // add node mode; clicking adds a node                                                                    
            case ADD_NODES:
                if (clicked == null){
                    // prompt user to name the node  
                     String name = JOptionPane.showInputDialog("Enter a name for the node.");
                    NodeData data;
                    if (name != null){
                        data = new NodeData(e.getPoint(), Color.RED, name);
                    }else{
                        data = new NodeData(e.getPoint(), Color.RED, "");
                    }
                    Graph<NodeData,EdgeData>.Node add = canvas.graph.addNode(data);
                    canvas.nodes.add(add);
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
                // remove node mode; clicking removes a node                                                              
            case RMV_NODES:
                if (clicked != null){
                    LinkedList<Graph<NodeData,EdgeData>.Edge> toRemove = new LinkedList<Graph<NodeData,EdgeData>.Edge>();
                    for (Graph<NodeData,EdgeData>.Edge edge : canvas.edges){
                        if (edge.getHead()==clicked || edge.getTail()==clicked){
                            toRemove.add(edge);
                        }
                    }
                    for (Graph<NodeData,EdgeData>.Edge edge : toRemove){
                        canvas.edges.remove(edge);
                    }
                    canvas.nodes.remove(clicked);
                    canvas.graph.removeNode(clicked);
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
                // dijkstra mode; displays cost from node clicked to other nodes                                          
            case DIJKSTRA:
                if (clicked != null){
                    double[] dijkstraCost = canvas.graph.distances(clicked);
                    StringBuilder path = new StringBuilder();
                    int i = 0;
                    for (Graph<NodeData,EdgeData>.Node node : canvas.nodes){
                        String name = node.getData().getName();
                        String dij = Double.toString(dijkstraCost[i]);
                        path.append("Path from "+name+" is "+dij+"\n");
                        i++;
                    }
                    // popup display of costs                                                                             
                    JOptionPane.showMessageDialog(null, path);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
                 break;
                // dft mode; starts dft at node clicked                                                                   
            case DFT:
                LinkedList<Graph<NodeData,EdgeData>.Edge> dft = new LinkedList<Graph<NodeData,EdgeData>.Edge>();
                if (clicked != null){
                    dft = canvas.graph.DFT(clicked);
                    LinkedList<Graph<NodeData,EdgeData>.Node> endpoints = canvas.graph.endpoints(dft);
                    for (int i = 0; i < endpoints.size(); i++){
                        if (i<dft.size()){
                            dft.get(i).getData().setColor(Color.ORANGE);
                        }
                        endpoints.get(i).getData().setColor(Color.ORANGE);
                    }
                    StringBuilder print = new StringBuilder();
                    for (Graph<NodeData,EdgeData>.Edge edge : dft){
                        print.append("-"+edge.getData().getName());
                    }
                    // prints dft path in popup window                                                                    
                    String result = "DFT path: "+print.substring(1);
                    JOptionPane.showMessageDialog(null, result);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
                //bft case; starts bft at node clicked                                                                    
            case BFT:
                LinkedList<Graph<NodeData,EdgeData>.Edge> bft = new LinkedList<Graph<NodeData,EdgeData>.Edge>();
                if (clicked != null){
                    bft = canvas.graph.BFT(clicked);
                    LinkedList<Graph<NodeData,EdgeData>.Node> endpoints = canvas.graph.endpoints(bft);
                    for (int i = 0; i < endpoints.size(); i++){
                        if (i<bft.size()){
                            bft.get(i).getData().setColor(Color.YELLOW);
                        }
                        endpoints.get(i).getData().setColor(Color.YELLOW);
                    }
                    // prints bft path in popup window                                                                    
                    StringBuilder print = new StringBuilder();
                    for (Graph<NodeData,EdgeData>.Edge edge : bft){
                        print.append("-"+edge.getData().getName());
                    }
                    String result = "BFT path: "+print.substring(1);
                    JOptionPane.showMessageDialog(null, result);
                } else {
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
            }
            canvas.repaint();
        }

        /** Records Node under mousepressed event in anticipation of possible drag */
        public void mousePressed(MouseEvent e) {
            nodePressed = findNearbyNode(e.getX(),e.getY());
        }

        /** Responds to mousereleased event */
        public void mouseReleased(MouseEvent e) {
            nodeReleased = findNearbyNode(e.getX(),e.getY());
            switch(mode){
                // add edge mode; drag from tail to head to create an edge                                                
            case ADD_EDGES:
                if (nodeReleased != null && nodePressed != null){
                    String name = JOptionPane.showInputDialog("Enter a name for the edge.");
                    Point releasedPt = nodeReleased.getData().getPoint();
                    Point pressedPt = nodePressed.getData().getPoint();
                    EdgeData data;

                    if (name!=null){
                        data = new EdgeData(pressedPt.distance(releasedPt), Color.BLACK, name);
                    } else{
                        data = new EdgeData(pressedPt.distance(releasedPt), Color.BLACK, "");
                    }
                    Graph<NodeData,EdgeData>.Edge add = canvas.graph.addEdge(data,nodePressed,nodeReleased);
                    canvas.edges.add(add);
                } else{
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
                // remove edge mode; drag from one node to another to remove edge                                         
            case RMV_EDGES:
                if (nodeReleased != null && nodePressed != null){
                    Graph<NodeData,EdgeData>.Edge edge = canvas.graph.getEdgeRef(nodePressed,nodeReleased);
                    Graph<NodeData,EdgeData>.Edge edge2 = canvas.graph.getEdgeRef(nodeReleased,nodePressed);
                    if (edge != null){
                        canvas.graph.removeEdge(edge);
                        canvas.edges.remove(edge);
                    } else {
                        canvas.graph.removeEdge(edge2);
                        canvas.edges.remove(edge2);
                    }
                }else{
                    Toolkit.getDefaultToolkit().beep();
                }
                break;
            }
            canvas.repaint();
        }

        /** Responds to mouse drag event */
        public void mouseDragged(MouseEvent e) {
            if (mode == InputMode.ADD_NODES && nodePressed!=null) {
                NodeData data = new NodeData(e.getPoint(),nodePressed.getData().getColor(),nodePressed.getData().getName());
                LinkedList<Graph<NodeData, EdgeData>.Node> neighbors = nodePressed.getNeighbors();
                //update node point and edge as user drags                                                                
                for (Graph<NodeData, EdgeData>.Node node : neighbors){
                    Graph<NodeData, EdgeData>.Edge edge = nodePressed.edgeTo(node);
                    Double distance = node.getData().getPoint().distance(e.getPoint());
                    edge.getData().setDistance(distance);
                }
                nodePressed.setData(data);
                canvas.repaint();
            }
        }

        // Empty but necessary to comply with MouseMotionListener interface.                                              
        public void mouseMoved(MouseEvent e) {}
    }
}
