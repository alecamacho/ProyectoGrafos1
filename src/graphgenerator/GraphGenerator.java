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
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
        
        Grafo g = new Grafo(500); //CREA UN OBJETO GRAFO
        //g.ErdosRenyi(500, true);
        //g.SimpleGeo((float) 0.3, false);
        //g.Gilbert((float) 0.25 ,true);
        g.Barabasi(10, true);
        
        g.generarArchivo("Model");
        for (int i = 0; i < g.getNum_nodos(); i++)
        {
            System.out.println(Arrays.toString(g.getNodos().get(i).getConexiones())) ;
        }
    }
    
}