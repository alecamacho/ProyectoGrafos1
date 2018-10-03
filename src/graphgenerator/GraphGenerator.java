/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphgenerator;

import java.io.IOException;
import java.util.Arrays;

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
        
        Grafo g = new Grafo(500); //CREA UN OBJETO GRAFO
        //g.ErdosRenyi(2000, false);
        //g.SimpleGeo((float) 0.4, false);
        //g.Gilbert((float) 0.5 ,false);
        g.Barabasi(4, false);
        
        g.generarArchivo("Barabasi500");
        for (int i = 0; i < g.getNum_nodos(); i++)
        {
            System.out.println(Arrays.toString(g.getNodos().get(i).getConexiones())) ;
        }

        Grafo salida = g.aux();//Crear grafo salida 
        Grafo DFSR = g.DFS_R(0, salida);
        DFSR.generarArchivo("DFS_R_Barabasi500");
        
        Grafo BFS = g.BFS(0);
        Grafo DFSI=g.DFS_I(0);
        BFS.generarArchivo("BFS_Barabasi500");
        DFSI.generarArchivo("DFS_I_Barabasi500");
        
        
        
        
    }
    
}
