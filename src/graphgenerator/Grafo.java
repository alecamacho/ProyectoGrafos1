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
import java.io.PrintStream;
import static java.lang.Math.random;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
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
    
    public void ErdosRenyi (int aristas_creadas, boolean autociclos,float max, float min)
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
        //random.setSeed(20);
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
                    Arista edge= new Arista(k,max,min);
                    this.getNodos().get(index_option1).agregar_conexion(index_option2,edge.getPeso());
                    this.getNodos().get(index_option2).agregar_conexion(index_option1,edge.getPeso());
                    edge.setOrigen(index_option1);
                    edge.setDestino(index_option2);
                    k = k+1;
                }
                else if ( autociclos == true)
                {
                    Arista edge= new Arista(k,max,min);
                    this.getNodos().get(index_option1).agregar_conexion(index_option2,edge.getPeso());
                    this.getNodos().get(index_option2).agregar_conexion(index_option1,edge.getPeso());
                    edge.setOrigen(index_option1);
                    edge.setDestino(index_option2);
                    k = k+1;                   
                }
            
        }
    }
    }
    public void Gilbert(float probability, boolean autociclos, float max, float min)
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
                        if (this.getNodos().get(i).getConexiones()[j] == 0)
                        {
                            Arista edge= new Arista(contador,max,min);
                            this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName(),edge.getPeso());
                            this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName(),edge.getPeso());
                            edge.setOrigen(i);
                            edge.setDestino(j);
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
                            Arista edge= new Arista(contador,max,min);
                            this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName(),edge.getPeso());
                            this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName(),edge.getPeso());
                            edge.setOrigen(i);
                            edge.setDestino(j);
                            contador =contador +1 ;
                        }
                    }
                }

                
                
            }
        }
        //System.out.println(contador);
    }
    
    public void SimpleGeo(float threshold, boolean autociclos, float max, float min)
    {
        int V;
        V = getNum_nodos(); // Asigna a V el numero de nodos
        for (int i = 0; V>i ; i++)
        {
            this.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
            this.getNodos().get(i).generare_random_coords(); //Genera las coordenadas (x,y) aleatorias para el nodo i
        }
        int contador=0;
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
                        Arista edge= new Arista(contador,max,min);
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName(),edge.getPeso());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName(),edge.getPeso());                     
                    }
                    contador++;
                }
                else if (autociclos == true)
                {
                    double distance;
                    distance = this.getNodos().get(i).node_distance(this.getNodos().get(j));
                    
                    if (distance < threshold)
                    {
                        Arista edge= new Arista(contador,max,min);
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName(),edge.getPeso());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName(),edge.getPeso());                     
                    } 
                    contador++;
                }
                
            }
        }
    }
    
    public void Barabasi (float max_degree, boolean autociclos, float max,float min)
    {
        int V;
        int contador=0;
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
                    float probability;
                    probability = 1 - (this.getNodos().get(i).getGrado() / (float)max_degree);
                    
                    //System.out.println(probability);
                    float random_number = rand.nextFloat();
                    if ((probability > random_number) && (this.getNodos().get(j).getGrado()<max_degree))
                    {
                        Arista edge= new Arista(contador,max,min);
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName(),edge.getPeso());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName(),edge.getPeso());
                        this.getNodos().get(i).increaseGrado();
                        this.getNodos().get(j).increaseGrado();
                        edge.setOrigen(i);
                        edge.setDestino(j);
                        contador++;
                        
                    }
                 else if (autociclos == true)
                 {
                    probability = 1 - (this.getNodos().get(i).getGrado() / (float)max_degree);
                    random_number = rand.nextFloat();
                    if (probability > random_number && (this.getNodos().get(j).getGrado()<max_degree))
                    {
                        Arista edge= new Arista(contador,max,min);
                        this.getNodos().get(i).agregar_conexion(this.getNodos().get(j).getName(),edge.getPeso());
                        this.getNodos().get(j).agregar_conexion(this.getNodos().get(i).getName(),edge.getPeso());
                        this.getNodos().get(i).increaseGrado();
                        this.getNodos().get(j).increaseGrado();
                        edge.setOrigen(i);
                        edge.setDestino(j);
                        contador++;
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
            bw.write("\""+(i)+"\" [label=\"N" + (i) +"(" +  this.getNodos().get(i).getPeso_acum() + ")\"];\r\n");
        }
        for(i = 0; i < V; i++) {
            k++;
            for(j = 0; j < k; j++) {
                    
                if (this.getNodos().get(i).getConexiones()[j] != 0){
                    float peso = this.getNodos().get(i).getConexiones()[j];
                    bw.write("\"" + (i) + "\"--\""+(j)+"\" [weight= " + peso + "];"  +  "\r\n"); 
                }
            }
        }
        bw.write("}");
        bw.close();
    }
    public Grafo BFS(int source){
        int V = this.getNum_nodos();
        LinkedList<Integer> siguiente = new LinkedList<>();
        HashSet <Integer> visitado = new HashSet<>();
        siguiente.add(source);
        Grafo Bfs = new Grafo(V);
        for (int i = 0; V>i ; i++)
            {
            Bfs.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
            }
        while (!siguiente.isEmpty()){
            int actual=siguiente.remove();
            if (!visitado.contains(actual)){
                for (int i=0;i<this.getNodos().get(actual).getConexiones().length; i++){
                    if (this.getNodos().get(actual).getConexiones()[i]!=0){
                        int hijo=i;
                        if (!visitado.contains(hijo) && !siguiente.contains(hijo)){
                            siguiente.add(hijo);
                            Bfs.getNodos().get(actual).agregar_conexion(hijo,this.getNodos().get(actual).getConexiones()[i]);
                            Bfs.getNodos().get(hijo).agregar_conexion(actual,this.getNodos().get(actual).getConexiones()[i]);
                        }
                            //System.out.println("hijos ");
                    }
                } 
            visitado.add(actual);
            }
        }
        return Bfs;
    }
    public Grafo DFS_I(int source){
        int V = this.getNum_nodos();
        Deque<Integer> siguiente = new ArrayDeque<>();
        //LinkedList<Integer> siguiente = new LinkedList<>();
        HashSet <Integer> visitado = new HashSet<>();
        siguiente.push(source);
        Grafo DFS_I = new Grafo(V);
        for (int i = 0; V>i ; i++)
            {
            DFS_I.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
            }
        while (!siguiente.isEmpty()){
            int actual=siguiente.pop();
            if (!visitado.contains(actual)){
                for (int i=0;i<this.getNodos().get(actual).getConexiones().length; i++){
                    if (this.getNodos().get(actual).getConexiones()[i]==1){
                        int hijo=i;
                        if (!visitado.contains(hijo) && !siguiente.contains(hijo)){
                            siguiente.push(hijo);
                            DFS_I.getNodos().get(actual).agregar_conexion(hijo,this.getNodos().get(actual).getConexiones()[i]);
                            DFS_I.getNodos().get(hijo).agregar_conexion(actual,this.getNodos().get(actual).getConexiones()[i]);
                        }
                            //System.out.println("hijos ");
                    }
                } 
            visitado.add(actual);
            }
        }
        return DFS_I;
    }
    public Grafo aux(){//Grafo salida para metodo recursivo
       int V=this.getNum_nodos(); // Asigna a V el numero de nodos
        Grafo aux = new Grafo(V);
        for (int i = 0; V>i ; i++)
            {
            aux.getNodos().put(i, new Nodo(i,V));// Agrega cada value , Nodo al hashmap
            }
        return aux;
    }
    
    public Grafo DFS_R(int source,Grafo Dfsr){
            int hijo = 0;
            System.out.println("Entre a DFS_R ");

                
                for (int i=0;i<this.getNodos().get(source).getConexiones().length; i++){
                    
                    if (this.getNodos().get(i).getVisitado()==0){
                        this.getNodos().get(source).setVisitado(1);
                        hijo = i;
                        if (this.getNodos().get(source).getConexiones()[i]==1){
                            hijo=i;
                            System.out.print("Ahora defino el nuevo source ");
                            System.out.println(hijo);
                            Dfsr.getNodos().get(source).getConexiones()[hijo]=this.getNodos().get(source).getConexiones()[hijo];
                            Dfsr.getNodos().get(hijo).getConexiones()[source]=this.getNodos().get(source).getConexiones()[hijo];
                            
                            DFS_R(hijo,Dfsr); 
                    }
                
                }
             
            }
        return Dfsr;
    }
    public Grafo Dijkstra(int s){
        
        
        Nodo source=this.getNodos().get(s);
        source.setPeso_acum(0);
        int V=this.getNum_nodos();
        PriorityQueue<Nodo> PQ = new PriorityQueue<>();
        
        //Crea el objeto grafo para el arbol de Dijkstra
        
        Grafo dijkstra= new Grafo(V);
        int[] ancestro = new int[V];
        ancestro[source.getName()]=source.getName();//ancestro[hijo]=padre
        for (int j=0;j<V;j++){
            PQ.add(this.getNodos().get(j));
            dijkstra.getNodos().put(j, new Nodo(j,V));// Agrega cada value , Nodo al hashmap
            //System.out.println(dijkstra.getNodos().get(j).getConexiones()[0]);
  
        }
        while(!PQ.isEmpty()){
            int padre=PQ.remove().getName();
            int num_con=this.getNodos().get(padre).getConexiones().length;
            for (int hijo=0;hijo<num_con; hijo++){
                float peso_arista=this.getNodos().get(padre).getConexiones()[hijo];
                
                if (peso_arista !=0) {
                    float distancia=peso_arista+this.getNodos().get(padre).getPeso_acum();
                    if (distancia<this.getNodos().get(hijo).getPeso_acum()){
                        this.getNodos().get(hijo).setPeso_acum(distancia);         
                        //if(this.getNodos().get(padre).getConexiones()[hijo]!=0){
                        ancestro[hijo]=padre;
                    //}
                    }
                }
            }
        }
        System.out.println("ancestro");

        System.out.println(Arrays.toString(ancestro));
        
        for(int a=0;a<V;a++){//i=hijo
            //if(s!=i){
                float peso= this.getNodos().get(ancestro[a]).getConexiones()[a];
                System.out.print("PESO:  "+peso);
                dijkstra.getNodos().get(a).agregar_conexion(ancestro[a],peso);
                System.out.print(" Conexion?  "+dijkstra.getNodos().get(a).getConexiones()[ancestro[a]]);
                dijkstra.getNodos().get(ancestro[a]).agregar_conexion(a,peso);
                System.out.println("  conexion?  "+dijkstra.getNodos().get(ancestro[a]).getConexiones()[a]);
                dijkstra.getNodos().get(a).setPeso_acum(this.getNodos().get(a).getPeso_acum());
            }
       // }
       
      return dijkstra;  
    }
            
    
    
}     