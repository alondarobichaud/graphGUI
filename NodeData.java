import java.util.*;
import java.awt.*;
import javax.swing.*;
/**                                                                                                                       
 *                                                                                                                        
 * Data type for Node class that hold a variety of information of the node                                                
 * @author Alonda Robichaud                                                                                               
 * @version CSC 212, December 10 2017                                                                                     
 *                                                                                                                        
 */

public class NodeData{
    /** Point where the node is drawn */
    private Point point;
    /** Node's color */
    private Color color;
    /** Node's name */
    private String name;

    /**Constructor initialises NodeData                                                                                   
     *                                                                                                                    
     * @param point, point where node is drawn                                                                            
     * @param color, node's color                                                                                         
     * @param name, nodes' name                                                                                           
     */
    public NodeData(Point point, Color color, String name){
        this.point = point;
        this.color = color;
        this.name = name;
    }

    /** Accessor for point                                                                                                
     * @return Point, point where node is displayed                                                                       
     */
    public Point getPoint(){
        return point;
    }

    /** Accessor for name                                                                                                 
     * @return String, node's name                                                                                        
     */
    public String getName(){
        return name;
    }
    
     /** Accessor for color                                                                                                
     * @return Color, color of node                                                                                       
     */
    public Color getColor(){
        return color;
    }

    /** Manipulator for Point                                                                                             
     * @param point, point to display node                                                                                
     */
    public void setPoint(Point point){
        this.point = point;
    }

    /** Manipulator for color                                                                                             
     * @param color, node color */
    public void setColor(Color color){
        this.color = color;
    }

    /** Manipulator for name                                                                                              
     * @param name, node name                                                                                             
     */
    public void setName(String name){
        this.name = name;
    }
}
