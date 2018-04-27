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
   
    //atributos
    private double x;
    private double y;
    private int width;
    private int heigth;
    private Imagen image;

    //constructores

    public Celda(double x, double y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }
    
     public Celda() {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeigth() {
        return heigth;
    }

    public void setHeigth(int heigth) {
        this.heigth = heigth;
    }

    public Imagen getImage() {
        return image;
    }

    public void setImage(Imagen image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Celda{" + "x=" + x + ", y=" + y + ", width=" + width + ", heigth=" + heigth + ", image=" + image + '}';
    }     
}