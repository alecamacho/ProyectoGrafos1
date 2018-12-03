/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphgenerator;

import java.util.Random;


/**
 *
 * @author alecp
 */
public class Arista {
    private int origen;
    private int destino;
    private float peso;
    private int name;
    

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getOrigen() {
        return origen;
    }

    public void setOrigen(int origen) {
        this.origen = origen;
    }

    public int getDestino() {
        return destino;
    }

    public void setDestino(int destino) {
        this.destino = destino;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

Arista(int nombre,float max, float min ){
    Random rand = new Random(nombre);
    
    float   w;
        w = (float)(min+rand.nextFloat()*max);
    this.peso = w;
}
//Arista(int nombre,float weight){
  //  this.peso= weight;
    
//}    
    
}
