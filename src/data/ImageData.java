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
import javafx.stage.FileChooser;

/**
 *
 * @author Heller
 */
public class ImageData {

    public boolean saveGrids(Grid grid,File folder) throws IOException, ClassNotFoundException {
        List<Grid> imagenList = new ArrayList<Grid>();
        String filepath="\\Grids.txt"; 
        File file = new File(folder.getPath()+filepath);
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            imagenList = (List<Grid>) aux;
            objectInputStream.close();
        }//if
        imagenList.add(grid);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
        output.writeUnshared(imagenList);//escribe el objeto
        output.close();//cerrar el archivo
        return true;
    }
    
     public boolean savePictures(Picture picture, File folder) throws IOException, ClassNotFoundException {
        List<Picture> imagenList = new ArrayList<Picture>();        
        String filepath="\\Pictures.txt";
        File file = new File(folder.getPath()+filepath);
        if (file.exists()) {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file));//lee el archivo
            Object aux = objectInputStream.readObject();
            imagenList = (List<Picture>) aux;
            objectInputStream.close();
        }//if
        imagenList.add(picture);
        ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(file));//escribe
        output.writeUnshared(imagenList);//escribe el objeto
        output.close();//cerrar el archivo
        return true;
    }
}
