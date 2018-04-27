/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Celda;
import domain.Imagen;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Heller
 */
public class ImageSelect extends Application {

    private final int WIDTH = 1400;
    private final int HEIGHT = 700;
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private Canvas canvas2;
    private TextField field;
    private Button button;
    private Label label;
    private TextField field3;
    private Label label3;
    private TextField field4;
    private Label label4;
    private PixelReader pixel; //se encarga de leer pixel por pixel
    private WritableImage writable; //convierte pixeles en una imagen
    private ArrayList<Image> imagenPartes = new ArrayList<>();
    int rows;
    int cols;
    int rowsM;
    int colsM;
    private String ruta;
    private Imagen imagen;
    private Image image;
    private GraphicsContext gc;
    private GraphicsContext gc2;
    private ScrollPane scrollPane;
    private ScrollPane scrollPane2;
    int chunkWidth; // determines the chunk width and height
    int chunkHeight;
    private Imagen matrizI[][];
    private Celda matrizC[][];
    int corte;
    int R;
    int C;
    private Imagen imageSelect;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Maker"); //Pone un titulo a la ventana
        initComponents(primaryStage); //inicializamos los componentes
        primaryStage.show(); //Mostramos la ventana
    }

    private void initComponents(Stage primaryStage) throws FileNotFoundException, IOException {
        this.button = new Button("Select an image");
        this.button.relocate(100, 605);
        this.button.setPrefSize(150, 30);
        this.button.setOnAction(buttonAction);

        this.label = new Label("Size");
        this.label.relocate(290, 610);
        this.label.setMinSize(150, 20);
        this.field = new TextField();
        this.field.relocate(320, 610);
        this.field.setPrefSize(70, 20);

        this.canvas2 = new Canvas(1400, 1400);
        canvas = new Canvas(1400, 1400);
        gc2 = this.canvas2.getGraphicsContext2D();

        this.label3 = new Label("Width");
        this.label3.relocate(700, 610);
        this.label3.setMinSize(150, 20);
        this.field3 = new TextField();
        this.field3.relocate(740, 610);
        this.field3.setPrefSize(70, 20);
        this.label4 = new Label("Heigth");
        this.label4.relocate(820, 610);
        this.label4.setMinSize(150, 20);
        this.field4 = new TextField();
        this.field4.relocate(865, 610);
        this.field4.setPrefSize(70, 20);
        this.pane = new Pane();
        this.pane.getChildren().add(this.button);
        this.pane.getChildren().add(this.label);
        this.pane.getChildren().add(this.field);
        this.pane.getChildren().add(this.label3);
        this.pane.getChildren().add(this.field3);
        this.pane.getChildren().add(this.label4);
        this.pane.getChildren().add(this.field4);
        this.scene = new Scene(this.pane, WIDTH, HEIGHT);
        scrollPane = new ScrollPane();
        scrollPane.setPrefSize(600, 600);
        canvas.setOnMouseClicked(eventSelectImage);
        canvas2.setOnMouseClicked(eventMosaic);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane2 = new ScrollPane();
        scrollPane2.setPrefSize(700, 600);
        scrollPane2.setLayoutX(650);
        scrollPane2.setContent(canvas2);
        scrollPane2.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane2.setHbarPolicy(ScrollBarPolicy.AS_NEEDED);
        primaryStage.setScene(this.scene);
    }

    //metodo con la que indicamos la funcion de los elementos, no se puede usar this por ser un metodo anidado
    EventHandler<ActionEvent> buttonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                // Establecer el filtro de extensión
                FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Archivos JPG (*.jpg)", "*.JPG", "(*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(filtro);
                File fileN = fileChooser.showOpenDialog(null);
                ruta = fileN.getPath();
                image = new Image(new FileInputStream(ruta));
                gc = canvas.getGraphicsContext2D();
                scrollPane.setContent(canvas);
                pane.getChildren().add(scrollPane);
                pane.getChildren().add(scrollPane2);
                loadImage(gc);
                draw(gc);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ImageSelect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ImageSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<javafx.scene.input.MouseEvent> eventSelectImage = new EventHandler<javafx.scene.input.MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            selectImage(x, y);
        }
    };

    EventHandler<javafx.scene.input.MouseEvent> eventMosaic = new EventHandler<javafx.scene.input.MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            System.out.println("Mosaic " + x + "," + y);//these co-ords are relative to the component
            dibujarI(x, y);
        }
    };

    public void selectImage(int x, int y) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((x >= matrizI[i][j].getX() && x <= matrizI[i][j].getX() + matrizI[i][j].getWidth())
                        && (y >= matrizI[i][j].getY() && y <= matrizI[i][j].getY() + matrizI[i][j].getHeigth())) {
                    imageSelect = new Imagen(matrizI[i][j].getX(), matrizI[i][j].getY(), chunkWidth, chunkWidth, matrizI[i][j].getImage());
                    System.out.println(imageSelect.toString());
                }
            }
        }
    }

    public void dibujarI(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrizC[i][j].getX() && x <= matrizC[i][j].getX() + matrizC[i][j].getWidth())
                        && (y >= matrizC[i][j].getY() && y <= matrizC[i][j].getY() + matrizC[i][j].getHeigth())) {
                    gc2.drawImage(imageSelect.getImage(), matrizC[i][j].getX(), matrizC[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());

                }
            }
        }
    }

    private void loadImage(GraphicsContext gc) throws IOException {
        rows = (int) image.getWidth() / Integer.parseInt(field.getText());
        cols = (int) image.getHeight() / Integer.parseInt(field.getText());
        R = rows * cols;
        chunkWidth = Integer.parseInt(field.getText());
        chunkHeight = Integer.parseInt(field.getText());
        canvas.setWidth(chunkWidth * rows);
        canvas.setHeight(chunkHeight * cols);
        matrizI = new Imagen[R][R];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                this.pixel = image.getPixelReader();//recibe los pixeles de la imagen
                this.writable = new WritableImage(this.pixel, x * (int) chunkWidth, y * (int) chunkHeight, (int) chunkWidth, (int) chunkHeight);
                imagenPartes.add(writable);
            }
        }
        rowsM = (int) (Integer.parseInt(field3.getText()) / chunkWidth);
        colsM = (int) (Integer.parseInt(field4.getText()) / chunkWidth);
        int tamañoMatrizC = rowsM * colsM;
        matrizC = new Celda[tamañoMatrizC][tamañoMatrizC];
        for (int i = 0; i <= rowsM; i++) {

            for (int j = 0; j <= colsM; j++) {
                gc2.strokeLine(0, i * chunkWidth, Integer.parseInt(field3.getText()), i * chunkWidth);
                gc2.strokeLine(j * chunkWidth, 0, j * chunkWidth, Integer.parseInt(field4.getText()));
                matrizC[i][j] = new Celda(i * chunkWidth, j * chunkHeight, chunkWidth, chunkWidth);
            }
        }
    }

    private void draw(GraphicsContext gc) {
        int iter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gc.drawImage(imagenPartes.get(iter), i * (chunkWidth + 2), j * (chunkHeight + 2));
                matrizI[i][j] = new Imagen(i * (chunkWidth), j * (chunkHeight), chunkWidth, chunkHeight, imagenPartes.get(iter));
                iter++;
            }
        }
    }
}
