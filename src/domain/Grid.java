/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;

/**
 *
 * @author Heller
 */
public class Grid implements Serializable {

    //atributos
    private double x;
    private double y;
    private int width;
    private int heigth;
    private Picture picture;
    private String path;

    //constructores
    public Grid(double x, double y, int width, int heigth, Picture picture) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.picture = picture;
    }

    public Grid(double x, double y, int width, int heigth, String path, Picture imagen) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public Grid(double x, double y, int width, int heigth, String path) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.path = path;
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

    public Picture getPicture() {
        return picture;
    }

    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }    
    
    @Override
    public String toString() {
        return "Grid{" + "x=" + x + ", y=" + y + ", width=" + width + ", heigth=" + heigth + ", picture=" + picture + '}';
    }
}
