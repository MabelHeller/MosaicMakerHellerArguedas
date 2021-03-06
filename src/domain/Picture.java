/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package domain;

import java.io.Serializable;
import javafx.scene.image.Image;

/**
 *
 * @author Heller
 */
public class Picture implements Serializable {

    //atributos
    private double x;
    private double y;
    private int width;
    private int heigth;
    private Image image;
    private String path;

    //constructores
    public Picture(double x, double y, int width, int heigth, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.image = image;
    }

    public Picture(double x, double y, int width, int heigth) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
    }

    public Picture(double x, double y, int width, int heigth, String path) {
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Picture{" + "x=" + x + ", y=" + y + ", width=" + width + ", heigth=" + heigth + ", image=" + image + '}';
    }

}
