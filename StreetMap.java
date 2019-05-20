/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Common;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author CHUWI
 */
public class StreetMap {

    public ArrayList<Node> nodes = new ArrayList();
    public ArrayList<Edge> edges = new ArrayList();

    public class Vector {

        public float X = 0.0f;
        public float Y = 0.0f;
    }

    public class LineParams {

        public float m = 0.0f;
        public float C = 0.0f;
    }

    public class Box {

        public Vector min = new Vector();
        public Vector max = new Vector();
    }

    public class GridNode {
        

        //public CopyOnWriteArrayList<Courier> couriers = new CopyOnWriteArrayList();
        public ArrayList< Edge> edges = new ArrayList();
        public ArrayList<Node> nodes = new ArrayList();
        int i;
        int j;
    }

    /**
     *
     * This could be userful
     */
    public class Grid {
        
        

        public Grid(int _gridSize, float _scale) {
            grid = new GridNode[_gridSize][_gridSize];
            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j].i = i;
                    grid[i][j].j = j;
                }
            }
            gridSize = _gridSize;
            scale = _scale;
        }

        /**
         * Specification: Rasterizes the edges into the grid
         * Please Note: This has never been run
         * @param edges 
         */
        public void PopulateGrid(ArrayList<Edge> edges) {

            for (int i = 0; i < edges.size(); i++) {
                Edge e = edges.get(i);

                int A_xIndex = (int) (e.A.pos.X / scale);
                int A_yIndex = (int) (e.A.pos.Y / scale);
                grid[A_xIndex][A_yIndex].edges.add(e);
                grid[A_xIndex][A_yIndex].nodes.add(e.A);

                int B_xIndex = (int) (e.B.pos.X / scale);
                int B_yIndex = (int) (e.B.pos.Y / scale);
                grid[B_xIndex][B_yIndex].edges.add(e);
                grid[B_xIndex][B_yIndex].nodes.add(e.B);

                if (A_xIndex != B_xIndex) {
                    LineParams lP = e.GetLineEq();

                    //if( lP == null ) break;
                    int nX = A_xIndex;
                    int nY = A_yIndex;

                    // hash edge into every node on the route 
                    // from A to B
                    if (lP.m != 0.0f) {
                        
                        int increment = (A_xIndex < B_xIndex) ? 1 : -1;

                        while (!((nX == B_xIndex) && (nY == B_yIndex))) {

                            nX = nX + increment;

                            // y = m x + c
                            float Y = lP.m * (float) (nX) + lP.C;

                            nY = (int) Y;
                            
                            if( nX < 0 || nX > this.gridSize )break;
                            if( nY < 0 || nY > this.gridSize )break;

                            grid[nX][nY].edges.add(e);
                        }

                    } else { // gradient is zero, stepping up in X

                        int increment = (A_xIndex < B_xIndex) ? 1 : -1;
                        
                        while (!(nX == B_xIndex)) {

                            nX = nX + increment;
                            
                            if( nX < 0 || nX > this.gridSize )break;

                            grid[nX][nY].edges.add(e);
                        }
                    }
                } else { // they are on a vertical line

                    int nX = A_xIndex;
                    int nY = A_yIndex;
                    
                    int increment = (A_yIndex < B_yIndex) ? 1 : -1;
                    
                    while (!(nY == B_yIndex)) {

                        nY = nY + increment;

                        if( nY < 0 || nY > this.gridSize ) break;
                        
                        grid[nX][nY].edges.add(e);
                    }
                }

            }

            for (int i = 0; i < grid.length; i++) {
                for (int j = 0; j < grid[i].length; j++) {
                    grid[i][j].i = i;
                    grid[i][j].j = j;
                }
            }
        }
        public float scale;
        public int gridSize = 0;
        public GridNode[][] grid;
    }

    public Grid grid;

    public StreetMap(int gridSize, float scale) {
        grid = new Grid(gridSize, scale);
    }
    
    ConcurrentHashMap<Integer, Location> courierLocs = new ConcurrentHashMap();
    
    

    public void UpdateCourierLocation(Courier c, Location pos) {
        int newLoc = 0;
        for (int i = 0; i < edges.size(); i++) {
            if (edges.get(i).IsLocOnEdge(pos)) {
                edges.get(i).couriers.put(c.getUserID(), c);
                newLoc = i;
            }
        }
        if (c.mapEdge != -1) {

            if (newLoc != c.mapEdge) {
                edges.get(c.mapEdge).couriers.remove(c.getUserID());

            }
        }
        c.mapEdge = newLoc;
    }

    public class Node {

        public ArrayList<Edge> edges = new ArrayList();
        public Location pos = new Location();

        public Node() {
        }

        public Node(Location p) {
            pos.X = p.X;
            pos.Y = p.Y;
        }

        public void ConnectToEdge(Edge e) {
            edges.add(e);
        }
    }

    public class Edge {

        public ConcurrentHashMap< Integer, Courier> couriers
                = new ConcurrentHashMap();

        public Node A = null;
        public Node B = null;

        public Edge(Node _A, Node _B) {

            A = _A;
            A.ConnectToEdge(this);

            B = _B;
            B.ConnectToEdge(this);
        }

        public LineParams GetLineEq() {
            float dX = B.pos.X - A.pos.X;
            float dY = B.pos.Y - A.pos.Y;
            LineParams lP = new LineParams();
            if (dY != 0.0f && dX != 0.0f) {
                lP.m = dY / dX;
                lP.C = A.pos.Y;
            } else {
                lP = null;
            }

            return lP;
        }

        public Location LerpNodeAtoB(float amount) {
            Location p = new Location();
            p.X = A.pos.X + (B.pos.X - A.pos.X) * amount;
            p.Y = A.pos.Y + (B.pos.Y - A.pos.Y) * amount;
            return p;
        }

        /**
         * Note: To be used with a point on the edge
         *
         * @param p
         * @return
         */
        public float GetLerpOfLocOnEdge(Location p) {
            float a1 = (p.X - A.pos.X) / (B.pos.X - A.pos.X);
            float a2 = (p.Y - A.pos.Y) / (B.pos.Y - A.pos.Y);
            if (a1 != a2) {
                return (a1 + a2) / 2.0f;
            }
            return a1;
        }

        public float MaxX(float p1, float p2) {
            return (p1 > p2) ? p1 : p2;
        }

        public float MinX(float p1, float p2) {
            return (p1 < p2) ? p1 : p2;
        }

        public Boolean IsPointInBox(Location p) {
            float maxX = MaxX(A.pos.X, B.pos.X);
            float minX = MaxX(A.pos.X, B.pos.X);
            float maxY = MaxX(A.pos.Y, B.pos.Y);
            float minY = MaxX(A.pos.Y, B.pos.Y);

            if (minX == maxX) {

                return (p.X > minX - 0.1) && (p.X < minX + 0.1)
                        && (p.Y > minY) && (p.Y < maxY);

            } else if (minY == maxY) {

                return (p.Y > minY - 0.1) && (p.Y < minY + 0.1)
                        && (p.Y > minX) && (p.Y < maxX);

            } else {

                return (p.X > minX) && (p.X < maxX)
                        && (p.Y > minY) && (p.Y < maxY);
            }

        }

        public Boolean IsLocOnEdge(Location p) {
            if (!IsPointInBox(p)) {
                return false;
            }
            // unlerp to find the interpolation values
            float alpha1 = (p.X - A.pos.X) / (B.pos.X - A.pos.X);
            float alpha2 = (p.Y - A.pos.Y) / (B.pos.Y - A.pos.Y);
            // if they are not the same, then the point isn't on the line
            if (Math.abs(alpha1 - alpha2) > 0.1) {
                return false;
            }
            // point is approximately on the line.
            return true;
        }

    }

}
