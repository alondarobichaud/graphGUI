import java.util.*;
import java.io.*;
import java.lang.*;

/**                                                                                                                       
 *                                                                                                                        
 * Implements a graph                                                                                                     
 * @author Alonda Robichaud                                                                                               
 * @version CSC 212, December 6 2017                                                                                      
 *                                                                                                                        
 */

public class Graph<V,E>{

    /** list of graph's nodes */
    private LinkedList<Node> nodes;
    /** list of graph's edges */
    private LinkedList<Edge> edges;

    /** Constructor initialises empty node and edge lists */
    public Graph(){
        nodes = new LinkedList<>();
        edges = new LinkedList<>();
    }

    /** Accessor for nodes                                                                                                
     * @param i, index of node in the nodes list                                                                          
     * @return Node, node accessed                                                                                        
     */
    public Node getNode(int i){
        return nodes.get(i);
    }

    /** Accessor for edges                                                                                                
     * @param i, index of node in the edges list                                                                          
     * @return Edge, edge accessed                                                                                        
     */
    public Edge getEdge(int i){
        return edges.get(i);
    }

    /** Accessor for specific edge                                                                                        
     * @param head, head of edge                                                                                          
     * @param tail, tail of edge                                                                                          
     * @return Edge, edge with given head and tail                                                                        
     */
     public Edge getEdgeRef(Node head, Node tail){
        for (Edge edge : edges){
            if (edge.getHead() == head && edge.getTail() == tail){
                return edge;
            }
        }
        return null;
    }
    
    /** Accessor for number of nodes                                                                                      
     * @return int, number of nodes                                                                                       
     */
    public int numNodes(){
        return nodes.size();
    }

    /** Accessor for number of edges                                                                                      
     * @return int, number of edges                                                                                       
     */
    public int numEdges(){
        return edges.size();
    }
    /** Adds a node                                                                                                       
     * @param data, data of node being added                                                                              
     * @return Node, new node added                                                                                       
     */
    public Node addNode(V data){
        Node node = new Node(data);
        nodes.add(node);
        return node;
    }

    /** Adds an edge                                                                                                      
     * @param data, data of edge being added                                                                              
     * @param head, head of edge being added                                                                              
     * @param tail, tail of edge being added                                                                              
     * @return Edge, new edge added                                                                                       
     */
    public Edge addEdge(E data, Node head, Node tail){
        Edge edge = new Edge(data, head, tail);
        edges.add(edge);
        tail.addEdgeRef(edge);
        head.addEdgeRef(edge);
        return edge;
    }
    
     /** Removes a node                                                                                                    
     * @param node, node being removed                                                                                    
     */
    public void removeNode(Node node){
        LinkedList<Node> nodeNeighbors = node.getNeighbors();
        for (Node n : nodeNeighbors){
            Edge edge = node.edgeTo(n);
            n.removeEdgeRef(edge);
            edges.remove(edge);
        }
        nodes.remove(node);
    }

    /** Removes an edge                                                                                                   
     * @param edge, edge being removed                                                                                    
     */
    public void removeEdge(Edge edge){
        Node head = edge.getHead();
        Node tail = edge.getTail();
        head.removeEdgeRef(edge);
        tail.removeEdgeRef(edge);
        edges.remove(edge);
    }
     /** Removes an edge                                                                                                   
     * @param head, head of edge being removed                                                                            
     * @param tail, tail of edge being removed                                                                            
     */
    public void removeEdge(Node head, Node tail){
        Edge edge = head.edgeTo(tail);
        if (edge != null){
            removeEdge(edge);
        }
    }

    /** Returns nodes not on a given list                                                                                 
     * @param group, hashset of nodes                                                                                     
     * @return HashSet<Node>, new hashset with new nodes                                                                  
  */
    public HashSet<Node> otherNode(HashSet<Node> group){
        HashSet<Node> nodeList = new HashSet<>();
        for (Node node : nodes){
            if (!group.contains(node)){
                nodeList.add(node);
            }
        }
        return nodeList;
    }
    
    /** Returns nodes that are endpoints of a list of edges                                                               
     * @param edges, hashset of edges                                                                                     
     * @return HashSet<Node>, hashset of nodes that are endpoints                                                         
     */
    public LinkedList<Node> endpoints(LinkedList<Edge> edges){
        LinkedList<Node> nodeList = new LinkedList<>();
        for (Edge edge : edges){
            if (!nodeList.contains(edge.getTail())){
                nodeList.add(edge.getTail());
            }
            if (!nodeList.contains(edge.getHead())){
                nodeList.add(edge.getHead());
            }
        }
        return nodeList;
    }
    
     /** Breadth-first traversal of the graph                                                                              
     * @param start, node where search starts                                                                             
     * @return LinkedList<Edge>, list of edges traversed                                                                  
     */
    public LinkedList<Edge> BFT(Node start){
        LinkedList<Node> queue = new LinkedList<Node>();
        HashSet<Node> seen = new HashSet<Node>();
        LinkedList<Edge> visited = new LinkedList<Edge>();
        queue.add(start);
        seen.add(start);
        while (!queue.isEmpty()){
            Node removed = queue.remove();
            LinkedList<Node> neighbors = removed.getNeighbors();
            for (Node neighbor : neighbors){
                if (!seen.contains(neighbor)){
                    visited.add(removed.edgeTo(neighbor));
                    queue.add(neighbor);
                    seen.add(neighbor);
                }
            }
        }
        return visited;
    }

    /** Entry method for DFT                                                                                              
     * @return LinkedList<Edge>, list of edges traversed                                                                  
     */
    public LinkedList<Edge> DFT(Node start){
        HashSet<Node> seen = new HashSet<Node>();
        LinkedList<Edge> visited = new LinkedList<Edge>();
        LinkedList<Edge> result = recurseDFT(start, seen, visited);
        return result;
    }
        
    /** Depth-first traversal of graph                                                                                    
     * @param start, node where search starts                                                                             
     * @param seen, list of nodes marked as seen                                                                          
     * @param visited, list of visited edges                                                                              
     * @return LinkedList<Edge>, list of edges visited                                                                    
     */
    public LinkedList<Edge> recurseDFT(Node start, HashSet<Node> seen, LinkedList<Edge> visited){
        if (!seen.contains(start)){
            for (Node neighbor : start.getNeighbors()){
                if (!seen.contains(neighbor)){
                    visited.add(start.edgeTo(neighbor));
                    seen.add(start);
                    recurseDFT(neighbor,seen,visited);
                }
            }
        }
        return visited;
    }

    /** Dijkstra's shortest-path algorithm to compute distances to nodes                                                  
     * @param start, node where search starts                                                                             
     * @return double[], array of all costs from start to all the other nodes                                             
     */
    public double[] distances(Node start){
        double[] result = new double[nodes.size()];
        Node current = start;
        Double cost;
        LinkedList<Node> unvisited = new LinkedList<Node>(this.nodes);
        HashMap<Node,Double> costList = new HashMap<Node,Double>();
        for (Node node : unvisited){
            if (node != start){
                costList.put(node,Double.POSITIVE_INFINITY);
            } else{
                costList.put(node,0.0);
            }
        }
        while (!unvisited.isEmpty()){
            LinkedList<Node> neighbors = current.getNeighbors();
            for (Node node : neighbors){
                if (current.edgeTo(node) != null && current != null){
                    Edge edge = current.edgeTo(node);
                    if (edge.getData() instanceof EdgeData){
                        @SuppressWarnings("unchecked")
                            EdgeData d = (EdgeData) edge.getData();
                        @SuppressWarnings("unchecked")
                            Double distance  = (Double) d.getDistance();
                            cost = distance;
                    }else{
                        cost = (Double) edge.getData();
                    }
                    if (unvisited.contains(node)){
                        double newCost = cost + costList.get(current);
                        if (newCost < costList.get(node)){
                            costList.remove(node);
                            costList.put(node, newCost);
                        } else{

                        }
                    }
                }
            }
            unvisited.remove(current);
            current = getMin(unvisited, costList);
        }
        int i=0;
        for (Node node : nodes){
            result[i]=costList.get(node);
            i++;
        }
        return result;
    }
    /** Method for dijkstra                                                                                               
     * Finds the unvisited not with the cheapest costList                                                                 
     * @param unvisited, list of unvisited nodes                                                                          
     * @param costList, HashMap with node as key and it's cost as value                                                   
     * @return Node, node with the cheapest cost                                                                          
     */
    private Node getMin(LinkedList<Node> unvisited, HashMap<Node,Double> costList){
        if (unvisited.size()>0){
            Node result = unvisited.get(0);
            for (Node node : unvisited){
                if (costList.get(result) > costList.get(node)){
                    result = node;
                }
            }
            return result;
        }
        return null;
    }

    /** Prints a representation of the graph */
    public void print(){
        for (Edge edge : edges){
            System.out.println("Edge: "+edge.getData()+"; its head: "+edge.getHead().getData()+"; its tail: "+edge.getTai\
l().getData());
        } for (Node node : nodes){
            LinkedList<Node> neighbors = node.getNeighbors();
            LinkedList<E> neighborData = new LinkedList<E>();
            for (Node n : neighbors){
                neighborData.add(node.edgeTo(n).getData());
            }
            System.out.println("Node: "+node.getData()+"; its edges: "+neighborData.toString());
        }
    }
    
    /** Checks the consistency of the graph */
    public void check(){
        for (Edge edge : edges) {
            Node head = edge.getHead();
            Node tail = edge.getTail();
            if (getEdgeRef(head, tail) != edge){
                System.out.println("Mismatching edge reference "+tail.getData()+"-"+head.getData());
            }
            if (!nodes.contains(head)){
                System.out.println(head.getData()+" not in nodes list");
            }
            if (!nodes.contains(tail)){
                System.out.println(tail.getData()+" not in nodes list");
            }
        }
        for (Node node : nodes){
            for (Node neighbor : node.getNeighbors()){
                Edge edge = node.edgeTo(neighbor);
                if (!edges.contains(edge)){
                    System.out.println("Edge between "+edge.getTail().getData()+" and "+edge.getHead().getData()+" is not\
 in edges list");
                }
                if (edge.getHead() != node && edge.getTail() != node){
                    System.out.println("Edge does not refer back to node "+node.getData());
                }
            }
        }
    }

    /** Nested class creating edges*/
    public class Edge{
        /** Head of the edge */
        private Node head;
        /** Tail of the edge */
        private Node tail;
        /** Data of edge */
        private E data;

        /** Constructor creates a new edge                                                                                
         * @param data, edge's data                                                                                       
         * @param head, the node where the edge is connecting to                                                          
         * @param tail, the node where the edge is connecting from                                                        
         */
        public Edge(E data, Node head, Node tail){
            this.data = data;
            this.head = head;
            this.tail = tail;
        }

        /** Accessor for data                                                                                             
         * @return E, data of edge                                                                                        
         */
        public E getData(){
            return data;
        }
        
        /** Accessor for endpoint #1                                                                                      
         * @return Node, the node that is the edge's head                                                                 
         */
        public Node getHead(){
            return head;
        }

        /** Accessor for endpoint #2                                                                                      
         * @return Node, the node that is the edge's tail                                                                 
         */
        public Node getTail(){
            return tail;
        }

        /** Accessor for opposite node                                                                                    
         * @param node, specified node                                                                                    
         * @return Node, the node that is opposite to specified node                                                      
         */
        public Node oppositeTo(Node node){
            Node result = null;
            if (node == head){
                result = tail;
            }else if (node == tail){
                result = head;
            }
            return result;
        }

     /** Manipulator for data */
        public void setData(E data){
            this.data = data;
        }

        /** Checks whether two edges connect the same endpoints                                                           
         * @return boolean, true if two edges connect the same endpoints                                                  
         */
        public boolean equals(Object o){
            if (o.getClass()==getClass()){
                @SuppressWarnings("unchecked")
                    Edge edge = (Edge) o;
                boolean equals = edge.getHead() == head && edge.getTail() == tail;
                boolean reverse = edge.getTail() == head && edge.getHead() == tail;
                if (reverse || equals){
                    return true;
                }
            }
            return false;
        }

        /** Redefined hashcode to match redefined equals                                                                  
         * @return int, new hashcode for the edge                                                                         
         */
        public int hashCode(){
            return head.hashCode()*tail.hashCode();
        }
    }

    /** Nested class creating nodes */
    public class Node{

        /** List of each node's edges */
        private HashSet<Edge> nodeEdges;
        /** Node's data */
        private V data;

        /** Constructor creates a disconnected node                                                                       
         * @param data, data of node                                                                                      
         */
        public Node(V data){
            this.data = data;
            nodeEdges = new HashSet<Edge>();
        }
        
          /** Accessor for data                                                                                             
         * @return V, specified node's data                                                                               
         */
        public V getData(){
            return data;
        }

        /** Manipulator for data                                                                                          
         * @param data, information used to change data                                                                   
         */
        public void setData(V data){
            this.data = data;
        }

        /** Returns a list of neighbors                                                                                   
         * @return LinkedList<Node>, list of neighbors                                                                    
         */
        public LinkedList<Node> getNeighbors(){
            LinkedList<Node> nodes = new LinkedList<Node>();
            for (Edge edge : nodeEdges){
                nodes.add(edge.oppositeTo(this));
            }
            return nodes;
        }
        
         /** Check if specified node has an edge connected to it                                                           
         * @param neighbor, node in question                                                                              
         * @return Edge, specified node's edge, null if there are none                                                    
         */
        public Edge edgeTo(Node neighbor){
            LinkedList<Node> neighborList = new LinkedList<Node>();
            neighborList = getNeighbors();

            if (neighborList.size()>0){
                for (Edge edge : nodeEdges){
                    if (edge.getHead() == neighbor || edge.getTail() == neighbor){
                        return edge;
                    }
                }
            }
            return null;
        }

        /** Adds an edge to the edge list                                                                                 
         * @param edge, edge being added to the list                                                                      
         */
        protected void addEdgeRef(Edge edge){
            nodeEdges.add(edge);
        }
        
                /** Removes an edge from the edge list                                                                            
         * @param edge, edge being removed from the list                                                                  
         */
        protected void removeEdgeRef(Edge edge){
            Node opposite = edge.oppositeTo(this);
            opposite.nodeEdges.remove(edge);
            nodeEdges.remove(edge);
        }
    }
}


