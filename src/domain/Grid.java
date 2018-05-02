/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Heller
 */
public class Grid implements Serializable{
   
    //atributos
    private double x;
    private double y;
    private int width;
    private int heigth;
    private Picture imagen;

    //constructores

     public Grid(double x, double y, int width, int heigth, Picture imagen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.imagen=imagen;
    }    
     
     public Grid(double x, double y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }  
    
     public Grid() {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }
//metodos accesores
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

    public Picture getImage() {
        return imagen;
    }

    public void setImage(Picture image) {
        this.imagen = image;
    }

    @Override
    public String toString() {
        return "Grid{" + "x=" + x + ", y=" + y + ", width=" + width + ", heigth=" + heigth + ", image=" + imagen + '}';
    }   
}