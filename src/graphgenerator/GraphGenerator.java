/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphgenerator;

import java.io.IOException;
import java.util.Arrays;
import java.util.PriorityQueue;

/**
 *
 * @author Alejandra
 */
public class GraphGenerator {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
       Grafo g = new Grafo(200); //CREA UN OBJETO GRAFO con n nodos 
        g.ErdosRenyi(800, false,50,1);
        //g.SimpleGeo((float) 0.4, false,50,1);
        //g.Gilbert((float) 0.5 ,false,50,1);
        //g.Barabasi(25, false,50,1);//rho máximo, autociclos?,peso máximo y peso mínimo de las aristas 
        
        g.generarArchivo("Erdos200_P3");
        //System.out.println("bara");
        for (int i = 0; i < g.getNum_nodos(); i++)
        {
            System.out.println(Arrays.toString(g.getNodos().get(i).getConexiones())) ;
        }

        //Grafo salida = g.aux();//Crear grafo salida 
        //Grafo DFSR = g.DFS_R(0, salida);
        //DFSR.generarArchivo("DFS_R_Barabasi500");
        
        //Grafo BFS = g.BFS(0);
        //Grafo DFSI=g.DFS_I(0);
        //BFS.generarArchivo("BFS_Barabasi500");
        //DFSI.generarArchivo("DFS_I_Barabasi500");
        
        Grafo dijkstra=g.Dijkstra(0);// Source 0
        //System.out.println("dijk_Bara_200");
        for (int i = 0; i < dijkstra.getNum_nodos(); i++)
        {
            System.out.println(Arrays.toString(dijkstra.getNodos().get(i).getConexiones())) ;
        }
        dijkstra.generarArchivo("dijk_Erdos_200");
        

        
        
        
        
    }
    
}
