/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphgenerator;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Alejandra
 */
public class Nodo {
    
    private int[] conexiones; 
    private int name;
    private int grado = 0;
    private float x_coor = 0;
    private float y_coor = 0;
    
    //CONSTRUCTOR
    public Nodo (int nombre, int L)
    {
        this.name = nombre;
        this.conexiones = new int[L];
    }
    // METODOS  DEL ARRAY CONEXIONES
    public int[] getConexiones() {
        return conexiones;
    }

    public void setConexiones(int[] conexiones) {
        this.conexiones = conexiones;
    }
    
    public void agregar_conexion(int nodo_id)
    {
        conexiones[nodo_id] = 1;
    }

    //METODOS DEL ATRIBUTO NOMBRE
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }
    
    //METODOS DEL ATRIBUTO GRADO DEL NODO
    public int getGrado() {
        return grado;
    }

    public void setGrado(int grado) {
        this.grado = grado;
    }
    
    public void increaseGrado() {
        this.grado = grado + 1;
    }
    
    
    //METODOS DE LAS COORDENADAS X, Y
    public float getX_coor() {
        return x_coor;
    }

    public void setX_coor(float x_coor) {
        this.x_coor = x_coor;
    }

    public float getY_coor() {
        return y_coor;
    }

    public void setY_coor(float y_coor) {
        this.y_coor = y_coor;
    }
    
    public void generare_random_coords() {
        Random rand = new Random();
        setX_coor(rand.nextFloat());
        setY_coor(rand.nextFloat());
    }
    
    public double node_distance(Nodo nodo_to_compare)
    {
        float nodo_1_x_coor = nodo_to_compare.getX_coor();
        float nodo_1_y_coor = nodo_to_compare.getY_coor(); 
        float nodo_2_x_coor = this.getX_coor();
        float nodo_2_y_coor = this.getY_coor();
        
        double distance;
        
        distance = Math.sqrt(Math.pow((nodo_1_x_coor - nodo_2_x_coor), 2) + Math.pow((nodo_1_y_coor - nodo_2_y_coor), 2)) ;
        
        return distance;
    }

       
    
}
