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
public class ProjectData {
    private List<Picture> pictureList = new ArrayList<Picture>();
    private List<Grid> gridList = new ArrayList<Grid>();

    //metodo en el que se guarda una matriz de cuadricula en un archivo serializado
    public boolean saveGrid(Grid grid[][], File folder, int rows, int cols) throws IOException, ClassNotFoundException {
        File file = new File(folder.getPath() + "\\Project.txt");
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            gridList = (List<Grid>) aux;
            objectInputStream.close();
        }//if
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gridList.add(grid[i][j]);
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
                output.writeUnshared(grid[i][j]);//escribe el objeto
                output.writeUnshared(gridList);//escribe el objeto
                output.close();//cerrar el archivo      
            }
        }
        return true;
    }
//metodo en el que se guarda una matriz de imagenes en un archivo serializado
    public boolean savePicture(Picture picture[][], File folder,int rows,int cols) throws IOException, ClassNotFoundException {
        File file = new File(folder.getPath() + "\\Project.txt");
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            pictureList = (List<Picture>) aux;
            objectInputStream.close();
        }//if
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pictureList.add(picture[i][j]);
                ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
                output.writeUnshared(picture[i][j]);//escribe el objeto
                output.writeUnshared(pictureList);
                output.close();//cerrar el archivo      
            }
        }
        return true;
    }
}