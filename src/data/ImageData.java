/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data;

import domain.Grid;
import domain.Picture;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Heller
 */
public class ImageData {

    public boolean saveG(Grid grid[][], File folder, int rows, int cols) throws IOException, ClassNotFoundException {
        File file = new File(folder.getPath() + "\\Project.txt");
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            objectInputStream.close();
        }//if
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
                output.writeUnshared(grid[i][j]);//escribe el objeto
                output.close();//cerrar el archivo      
            }
        }
        return true;
    }

    public boolean savePicture(Picture picture[][], File folder,int rows,int cols) throws IOException, ClassNotFoundException {
        File file = new File(folder.getPath() + "\\Project.txt");
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            objectInputStream.close();
        }//if
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
                output.writeUnshared(picture[i][j]);//escribe el objeto
                output.close();//cerrar el archivo      
            }
        }
        return true;
    }
}