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
import java.util.ArrayList;
import java.util.List;
import javafx.scene.image.Image;

/**
 *
 * @author Heller
 */
public class Imagen {

    //atributos
    private double x;
    private double y;
    private int width;
    private int heigth;
    private Image image;

    //constructores
    public Imagen(double x, double y, int width, int heigth, Image image) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.image = image;
    }

    public Imagen() {
        this.x = x;
        this.y = y;
        this.width = width;
        this.heigth = heigth;
        this.image = image;
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

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "Imagen{" + "x=" + x + ", y=" + y + ", width=" + width + ", heigth=" + heigth + ", image=" + image + '}';
    }

    public int sizeInBytes() {
        return 2 + 2 + 4 + 4 + 8;
    }

    public boolean saveBooks(Imagen imagen) throws IOException, ClassNotFoundException {
        List<Imagen> imagenList = new ArrayList<Imagen>();
        File file = new File("imagenList.txt");
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            imagenList = (List<Imagen>) aux;
            objectInputStream.close();
        }//if
        imagenList.add(imagen);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
        output.writeUnshared(imagenList);//escribe el objeto
        output.close();//cerrar el archivo
        return true;
    }
}
