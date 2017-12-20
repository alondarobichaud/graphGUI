import java.util.*;
import java.awt.*;
import javax.swing.*;
/**                                                                                                                       
 *                                                                                                                        
 * Implements a graphical canvas that displays a list of nodes and edges                                                  
 * @author Alonda Robichaud                                                                                               
 * @version CSC 212, December 10 2017                                                                                     
 *                                                                                                                        
 */
class GraphCanvas extends JComponent {
    /** The nodes */
    LinkedList<Graph<NodeData, EdgeData>.Node> nodes;

    /** The edges */
    LinkedList<Graph<NodeData, EdgeData>.Edge> edges;

    /** The graph */
    Graph<NodeData, EdgeData> graph;


    /** Constructor creates a graph and sets window size*/
    public GraphCanvas() {
        nodes = new LinkedList<Graph<NodeData, EdgeData>.Node>();
        edges = new LinkedList<Graph<NodeData, EdgeData>.Edge>();
        graph = new Graph<NodeData,EdgeData>();
        setMinimumSize(new Dimension(300,500));
        setPreferredSize(new Dimension(300,500));
    }

    /**                                                                                                                   
     * Paints a red circle ten pixels in diameter at each point.                                                          
     * @param g The graphics object to draw with                                                                          
     */
    public void paintComponent(Graphics g) {
        // draw edges                                                                                                     
        for (Graph<NodeData,EdgeData>.Edge edge : edges){
            Point head = edge.getHead().getData().getPoint();
            int hx = head.x;
            int hy = head.y;
            int tx = edge.getTail().getData().getPoint().x;
            int ty = edge.getTail().getData().getPoint().y;
            int cx = (hx+tx)/2;
            int cy = (hy+ty)/2;
            g.setColor(edge.getData().getColor());
            g.drawLine(hx, hy, tx, ty);
            g.setColor(Color.RED);
             g.drawString(edge.getData().getName(),cx-5,cy-5);
        }
        // draw nodes                                                                                                     
        for (Graph<NodeData,EdgeData>.Node node : nodes){
            int x = node.getData().getPoint().x;
            int y = node.getData().getPoint().y;
            g.setColor(node.getData().getColor());
            g.fillOval(x-10,y-10,20,20);
            g.setColor(Color.BLACK);
            g.drawString(node.getData().getName(),x,y+5);
        }
    }
}
