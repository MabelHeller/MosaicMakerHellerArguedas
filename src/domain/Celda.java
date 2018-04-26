/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

/**
 *
 * @author Heller
 */
public class Celda {
    private int x;
    private int y;
    private Imagen imagen;

    public Celda(int x, int y, Imagen imagen) {
        this.x = x;
        this.y = y;
        this.imagen = imagen;
    }
    
    public Celda() {
        this.x = x;
        this.y = y;
        this.imagen = imagen;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Celda{" + "x=" + x + ", y=" + y + ", imagen=" + imagen + '}';
    }    
}
