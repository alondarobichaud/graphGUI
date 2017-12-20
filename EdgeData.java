mport java.util.*;
import java.awt.*;
import javax.swing.*;
/**                                                                                                                       
 *                                                                                                                        
 * Data type for Edge class that hold a variety of information of the edge                                                
 * @author Alonda Robichaud                                                                                               
 * @version CSC 212, December 10 2017                                                                                     
 *                                                                                                                        
 */

public class EdgeData{

    private Color color;
    private double distance;
    private String name;

    /**Constructor initialises EdgeData                                                                                   
     *                                                                                                                    
     * @param distance, numeric value of edge (distance between head and tail)                                            
     * @param color, color edge is drawn                                                                                  
     * @param name, name of edge                                                                                          
     */

    public EdgeData(double distance, Color color, String name){
        this.color = color;
        this.distance = distance;
        this.name = name;
    }

    /** Accessor for distance                                                                                             
     * @return double, distance between head and tail                                                                     
     */
    public double getDistance(){
        return distance;
    }

    /** Accessor for name                                                                                                 
     * @return String, edge's name                                                                                        
     */
    public String getName(){
        return name;
    }
    
    /** Accessor for color                                                                                                
     * @return Color, edge's color                                                                                        
     */
    public Color getColor(){
        return color;
    }

    /** Manipulator for distance                                                                                          
     * @param distance, distance from head to tail                                                                        
     */
    public void setDistance(double distance){
        this.distance = distance;
    }

    /** Manipulator for name                                                                                              
     * @param name, edge's name                                                                                           
     */
    public void setName(String name){
        this.name = name;
    }

    /** Manipulator for color                                                                                             
     * @param color, color of edge                                                                                        
     */
    public void setColor(Color color){
        this.color = color;
    }
}
