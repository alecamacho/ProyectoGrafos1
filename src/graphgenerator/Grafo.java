/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphgenerator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import static java.lang.Math.random;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;

/**
 *
 * @author Alejandra
 */
public class Grafo {


    private HashMap<Integer, Nodo> nodos = new HashMap<>();
    private int num_nodos;
    
    public Grafo(int a)
    {
        this.num_nodos = a;
    }
    
    public void ErdosRenyi (int aristas_creadas, boolean autociclos)
    {
        int V;
        V = getNum_nodos(); // Asigna a V el numero de nodos
        for (int i = 0; V>i ; i++)
        {
            this.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
        }
        
        int[] options = IntStream.range(0, V).toArray(); //Array del 0 al numero de nodos
        //System.out.println(Arrays.toString(options));
        java.util.Random random = new java.util.Random(); // Crea objeto random
        int k;
        k = 0;
        //COMIENZA A GENERAR LAS CONEXIONES DE LAS ARISTAS
        while (k < aristas_creadas)
        {
            //ELIGE AL AZAR 2 NODOS. EN ESTE CASO LAS VARIABLES INDEX SONEL KEY DEL HASHMAP
            int index_option1 = random.nextInt(options.length); // Regresa un numero aleatorio de entre 0 y length
            //System.out.println(index_option1);
            int index_option2 = random.nextInt(options.length); // Regresa un numero aleatorio de entre 0 y length
            //System.out.println(index_option2);
            
            //SI ESOS DOS NODOS AUN NO ESTAN CONECTADOS, LOS CONECTA
            if (this.getNodos().get(index_option1).getConexiones()[index_option2] != 1)
            {
                //SI SE ELIGEN LOS MISMOS NODOS, LOS CONECTA SOLO SI SE ACEPTAN LOS AUTOCICLOS
                if (index_option1 != index_option2)
                {
                    this.getNodos().get(index_option1).agregar_conexion(index_option2);
                    this.getNodos().get(index_option2).agregar_conexion(index_option1);
                    k = k+1;
                }
                else if ( autociclos == true)
                {
                    this.getNodos().get(index_option1).agregar_conexion(index_option2);
                    this.getNodos().get(index_option2).agregar_conexion(index_option1);
                    k = k+1;                   
                }
            }
        }
    }
    
    public void Gilbert(float probability, boolean autociclos)
    {
        int V;
        V = getNum_nodos(); // Asigna a V el numero de nodos
        int v_medios = V/2;
        for (int i = 0; V>i ; i++)
        {
            this.getNodos().put(i, new Nodo(i,V));// Agrega cada (value , Nodo) al hashmap
        }
        
        Random rand = new Random();
        int contador  = 0;
        
        
        for (int i = 0; V>i;i++)
        {
            for (int j=0; V>j; j++)
            {
                float random_number = rand.nextFloat();
                //System.out.println(random_number);
                
                if (i != j)
                {
                    if (random_number < probability)
                    {
                        if (this.getNodos().get(i).getConexiones()[j] != 1)
                        {
                            this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName());
                            this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName()); 
                            contador = contador +1 ;
                        }
                    }                    
                }
                
                else if (autociclos == true)
                {
                    if (random_number < probability)
                    {
                        if (this.getNodos().get(i).getConexiones()[j] != 1)
                        {
                            this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName());
                            this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName());  
                            contador =contador +1 ;
                        }
                    }
                }

                
                
            }
        }
        //System.out.println(contador);
    }
    
    public void SimpleGeo(float threshold, boolean autociclos)
    {
        int V;
        V = getNum_nodos(); // Asigna a V el numero de nodos
        for (int i = 0; V>i ; i++)
        {
            this.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
            this.getNodos().get(i).generare_random_coords(); //Genera las coordenadas (x,y) aleatorias para el nodo i
        }
        
        for (int i=0 ; i < this.getNum_nodos(); i++)
        {
            for  (int j = 0; j < this.getNum_nodos(); j++)
            {
                
                if (i != j)
                {
                    double distance;
                    distance = this.getNodos().get(i).node_distance(this.getNodos().get(j));
                    
                    if (distance < threshold)
                    {
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName());                     
                    }

                }
                else if (autociclos == true)
                {
                    double distance;
                    distance = this.getNodos().get(i).node_distance(this.getNodos().get(j));
                    
                    if (distance < threshold)
                    {
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName());                     
                    }                   
                }
                
            }
        }
    }
    
    public void Barabasi (int max_degree, boolean autociclos)
    {
        int V;
        V = getNum_nodos(); // Asigna a V el numero de nodos
        for (int i = 0; V>i ; i++)
        {
            this.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
        } 
        Random rand = new Random();
        for (int i=0; i<V; i++)
        {
            for (int j=0; j<V; j++)
            {
                if (i!=j)
                {
                    double probability;
                    probability = 1 - (this.getNodos().get(i).getGrado() / (float)max_degree);
                    //System.out.println(probability);
                    float random_number = rand.nextFloat();
                    if (probability > random_number)
                    {
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName());
                        this.getNodos().get(i).increaseGrado();
                        this.getNodos().get(j).increaseGrado();
                        
                    }
                 else if (autociclos == true)
                 {
                    probability = 1 - (this.getNodos().get(i).getGrado() / (float)max_degree);
                    random_number = rand.nextFloat();
                    if (probability > random_number)
                    {
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName());
                        this.getNodos().get(i).increaseGrado();
                        this.getNodos().get(j).increaseGrado();
                    }                     
                 }
                }
            }
            //System.out.println(this.getNodos().get(i).getGrado());
        }
        
        
    }
            
    public HashMap<Integer, Nodo> getNodos() {
        return nodos;
    }

    public void setNodos(HashMap<Integer, Nodo> nodos) {
        this.nodos = nodos;
    }

    public int getNum_nodos() {
        return num_nodos;
    }

    public void setNum_nodos(int num_nodos) {
        this.num_nodos = num_nodos;
    }

    public void generarArchivo(String modelo) throws IOException {
        int i = 1, j, k = 0, l, x, flag = 1;
        String path, ruta = "", nombre = "";
        path = "./";
        File archivo = new File(ruta);
        BufferedWriter bw;
        while( flag == 1 ) {
            nombre = modelo;
            ruta = path + nombre + ".gv";
            archivo = new File(ruta);
            if(archivo.exists()) {
                i++;
            } else {
                flag = 0;
            }
        }         
        bw = new BufferedWriter(new FileWriter(archivo));
        bw.write("graph {"+"\r\n");
        int V = this.getNum_nodos();
        for(i = 0; i < V; i++) {
            bw.write("\""+(i)+"\";\r\n");
        }
        for(i = 0; i < V; i++) {
            k++;
            for(j = 0; j < k; j++) {
                    
                if (this.getNodos().get(i).getConexiones()[j] == 1){
                    bw.write("\""+(i)+"\"--\""+(j)+"\";"+"\r\n"); 
                }
            }
        }
        bw.write("}");
        bw.close();
    }

    
}

