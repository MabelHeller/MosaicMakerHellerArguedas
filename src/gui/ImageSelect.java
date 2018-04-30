/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import domain.Cuadro;
import domain.Imagen;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;
import javafx.scene.text.Text;

/**
 *
 * @author Heller
 */
public class ImageSelect extends Application {

    //atributos
    private final int WIDTH = 1400;
    private final int HEIGHT = 700;
    private Scene scene;
    private Pane pane;
    private Canvas canvas;
    private Canvas canvas2;
    private TextField field;
    private Button button;
    private Button buttonRotateL;
    private Button buttonRotateR;
    private Button buttonDeleted;
    private Button buttonSaveImage;
    private Button buttonSave;
    private Label label;
    private TextField field3;
    private Label label3;
    private TextField field4;
    private Label label4;
    private PixelReader pixel; //se encarga de leer pixel por pixel
    private WritableImage writable; //convierte pixeles en una imagen
    private ArrayList<Image> imagenPartes = new ArrayList<>();
    private int rows;
    private int cols;
    private int rowsM;
    private int colsM;
    private String path;
    private Image image;
    private GraphicsContext gc;
    private GraphicsContext gc2;
    private ScrollPane scrollPane;
    private ScrollPane scrollPane2;
    private int chunkWidth; // determines the chunk width and height
    private int chunkHeight;
    private SnapshotParameters snapshot;
    private Imagen matrixI[][];
    private Cuadro matrixC[][];
    private int sizeMatrixI;
    private Imagen imageSelect;
    private Imagen imagenCambiada;
    private ImageView imageS;
    private Image imagenRotate;
    private int x;
    private int y;
    private int sizeMatrixC;
    Image imageBackground;
    BackgroundSize backgroundSize;
    BackgroundImage backgroundImage;
    Background background;

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
        this.buttonSaveImage = new Button("Save image of mosaic");
        this.buttonSaveImage.relocate(437, 605);
        this.buttonSaveImage.setPrefSize(140, 30);
        this.buttonSaveImage.setOnAction(buttonSavePng);
        this.buttonSave = new Button("Save");
        this.buttonSave.relocate(593, 605);
        this.buttonSave.setPrefSize(70, 30);
        this.buttonSave.setOnAction(buttonSavePng);
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
        this.buttonRotateL = new Button("Rotate left");
        this.buttonRotateL.relocate(945, 605);
        this.buttonRotateL.setPrefSize(90, 30);
        this.buttonRotateL.setOnAction(buttonRotateLAction);
        this.buttonRotateR = new Button("Rotate Right");
        this.buttonRotateR.relocate(1040, 605);
        this.buttonRotateR.setPrefSize(90, 30);
        this.buttonRotateR.setOnAction(buttonRotateRAction);
        this.buttonDeleted = new Button("Deleted");
        this.buttonDeleted.relocate(1135, 605);
        this.buttonDeleted.setPrefSize(90, 30);
        this.buttonDeleted.setOnAction(buttonDeleteAction);
        this.pane = new Pane();
        this.pane.getChildren().add(this.button);
        this.pane.getChildren().add(this.label);
        this.pane.getChildren().add(this.field);
        this.pane.getChildren().add(this.label3);
        this.pane.getChildren().add(this.field3);
        this.pane.getChildren().add(this.label4);
        this.pane.getChildren().add(this.field4);
        this.pane.getChildren().add(this.buttonRotateL);
        this.pane.getChildren().add(this.buttonRotateR);
        this.pane.getChildren().add(this.buttonDeleted);
        this.pane.getChildren().add(this.buttonSaveImage);
        this.pane.getChildren().add(this.buttonSave);
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
        imageBackground = new Image("assets/fondo.jpg");
        backgroundSize = new BackgroundSize(2000, 1100, false, false, false, false);
        backgroundImage = new BackgroundImage(imageBackground, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT, backgroundSize);
        background = new Background(backgroundImage);
        this.pane.setBackground(background);

        primaryStage.setScene(this.scene);
    }

    /**
     * ******************EVENTO EN EL ABRE EL FILECHOOSER PARA BUSCAR LA NUEVA
     * IMAGEN QUE SE USARA*************************
     */
    //metodo con la que indicamos la funcion de los elementos, no se puede usar this por ser un metodo anidado
    EventHandler<ActionEvent> buttonAction = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooser = new FileChooser();
                FileChooser.ExtensionFilter filtro = new FileChooser.ExtensionFilter("Archivos JPG (*.jpg)", "*.JPG", "(*.png)", "*.PNG");
                fileChooser.getExtensionFilters().addAll(filtro);
                File fileN = fileChooser.showOpenDialog(null);
                path = fileN.getPath();
                image = new Image(new FileInputStream(path));
                gc = canvas.getGraphicsContext2D();
                scrollPane.setContent(canvas);
                pane.getChildren().add(scrollPane);
                pane.getChildren().add(scrollPane2);
                cropImage(gc);
                draw(gc);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(ImageSelect.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ImageSelect.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /**
     * ******Este metodo corta la imagen en el tamaño que ingresa el usuario y
     * forma la cuadricula con el tamaño asignado******
     */
    private void cropImage(GraphicsContext gc) throws IOException {
        rows = (int) image.getWidth() / Integer.parseInt(field.getText());
        cols = (int) image.getHeight() / Integer.parseInt(field.getText());
        sizeMatrixI = rows * cols;
        chunkWidth = Integer.parseInt(field.getText());
        chunkHeight = Integer.parseInt(field.getText());
        canvas.setWidth(chunkWidth * rows);
        canvas.setHeight(chunkHeight * cols);
        matrixI = new Imagen[sizeMatrixI][sizeMatrixI];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                this.pixel = image.getPixelReader();//recibe los pixeles de la imagen
                this.writable = new WritableImage(this.pixel, x * (int) chunkWidth, y * (int) chunkHeight, (int) chunkWidth, (int) chunkHeight);
                imagenPartes.add(writable);
            }
        }
        rowsM = (int) (Integer.parseInt(field3.getText()) / chunkWidth);
        colsM = (int) (Integer.parseInt(field4.getText()) / chunkWidth);
        sizeMatrixC = rowsM * colsM;
        canvas2.setWidth(chunkWidth * rowsM);
        canvas2.setHeight(chunkHeight * colsM);
        matrixC = new Cuadro[sizeMatrixC][sizeMatrixC];
        for (int i = 0; i <= rowsM; i++) {
            for (int j = 0; j <= colsM; j++) {
                gc2.strokeLine(0, i * chunkWidth, Integer.parseInt(field3.getText()), i * chunkWidth);
                gc2.strokeLine(j * chunkWidth, 0, j * chunkWidth, Integer.parseInt(field4.getText()));
                matrixC[i][j] = new Cuadro(i * chunkWidth, j * chunkHeight, chunkWidth, chunkWidth, null);
            }
        }
    }

    /**
     * ********************DIBUJA LA IMAGEN CORTADA*******************
     */
    private void draw(GraphicsContext gc) {
        int iter = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                gc.drawImage(imagenPartes.get(iter), i * (chunkWidth + 2), j * (chunkHeight + 2));
                matrixI[i][j] = new Imagen(i * (chunkWidth), j * (chunkHeight), chunkWidth, chunkHeight, imagenPartes.get(iter));
                iter++;
            }
        }
    }
    /**
     * ***************EVENTO DEL MOUSE EN EL QUE SE SELECIONA UN PEDAZO DE LA
     * IMAGEN CORTADA*******
     */
    EventHandler<MouseEvent> eventSelectImage = new EventHandler<MouseEvent>() {
        @Override
        public void handle(javafx.scene.input.MouseEvent e) {
            int x = (int) e.getX();
            int y = (int) e.getY();
            selectImage(x, y);
        }
    };

    /**
     * ************METODO EN EL QUE SE SELECCIONA UNA IMAGEN PRODUCTO DE LA
     * IMAGEN ORIGINAL*************
     */
    /**
     * *******LO QUE SE HACE ES RECORRER LA MATRIZ DE IMAGENES Y LUEGO GUARDA
     * EN UNA VARIABLE LA IMAGEN QUE SE SELECCIONO PARA LUEGO USARLA************
     */
    public void selectImage(int x, int y) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if ((x >= matrixI[i][j].getX() && x <= matrixI[i][j].getX() + matrixI[i][j].getWidth())
                        && (y >= matrixI[i][j].getY() && y <= matrixI[i][j].getY() + matrixI[i][j].getHeigth())) {
                    imageSelect = new Imagen(matrixI[i][j].getX(), matrixI[i][j].getY(), chunkWidth, chunkWidth, matrixI[i][j].getImage());
                }
            }
        }
    }

    /**
     * *****DIBUJA LA IMAGEN EN LA CUADRICULA**********
     */
    public void dibujarI(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrixC[i][j].getX() && x <= matrixC[i][j].getX() + matrixC[i][j].getWidth())
                        && (y >= matrixC[i][j].getY() && y <= matrixC[i][j].getY() + matrixC[i][j].getHeigth())) {
                    if (matrixC[i][j].getImage() == null) {
                        gc2.drawImage(imageSelect.getImage(), matrixC[i][j].getX(), matrixC[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());
                        imageSelect = new Imagen(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imageSelect.getImage());
                        matrixC[i][j] = new Cuadro(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imageSelect);
                    }
                }
            }
        }
    }
    /**
     * *********EVENTO DEL MOUSE EN EL QUE SE SELECIONA UNA PARTE DE LA
     * CUADRICULA PARA DIBUJAR LA IMAGEN*******
     */
    EventHandler<MouseEvent> eventMosaic = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent e) {
            x = (int) e.getX();
            y = (int) e.getY();
            dibujarI(x, y);
        }
    };

    /**
     * ************ROTAR A LA IZQUIERDA*************
     */
    public void RotateLeft(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrixC[i][j].getX() && x <= matrixC[i][j].getX() + matrixC[i][j].getWidth())
                        && (y >= matrixC[i][j].getY() && y <= matrixC[i][j].getY() + matrixC[i][j].getHeigth())) {
                    imagenRotate = matrixC[i][j].getImage().getImage();
                    imageS = new ImageView(imagenRotate);
                    imageS.setRotate(imageS.getRotate() - 90); //rota la imagen 90 gredos sentido del reloj
                    imagenRotate = imageS.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
                    gc2.drawImage(this.imagenRotate, matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth());
                    imagenRotate = imageS.snapshot(this.snapshot, null);
                    imagenCambiada = new Imagen(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imagenRotate);
                    matrixC[i][j] = new Cuadro(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imagenCambiada);
                }
            }
        }
    }
    /**
     * ***********EVENTO DEL MOUSE EN EL QUE SE ROTA LA IMAGEN
     * SELECCIONADA*******
     */
    EventHandler<ActionEvent> buttonRotateLAction = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            RotateLeft(x, y);
        }
    };

    /**
     * ***************ROTAR A LA DERECHA********************
     */
    public void RotateRight(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrixC[i][j].getX() && x <= matrixC[i][j].getX() + matrixC[i][j].getWidth())
                        && (y >= matrixC[i][j].getY() && y <= matrixC[i][j].getY() + matrixC[i][j].getHeigth())) {
                    imagenRotate = matrixC[i][j].getImage().getImage();
                    imageS = new ImageView(imagenRotate);
                    imageS.setRotate(imageS.getRotate() + 90); //rota la imagen 90 gredos sentido del reloj
                    imagenRotate = imageS.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
                    gc2.drawImage(this.imagenRotate, matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth());
                    imagenRotate = imageS.snapshot(this.snapshot, null);
                    imagenCambiada = new Imagen(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imagenRotate);
                    matrixC[i][j] = new Cuadro(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imagenCambiada);
                }
            }
        }
    }
    EventHandler<ActionEvent> buttonRotateRAction = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            RotateRight(x, y);
        }
    };

    /**
     * *****************ELIMINAR IMAGEN******************
     */
    public void RemoveImage(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrixC[i][j].getX() && x <= matrixC[i][j].getX() + matrixC[i][j].getWidth())
                        && (y >= matrixC[i][j].getY() && y <= matrixC[i][j].getY() + matrixC[i][j].getHeigth())) {
                    imagenRotate = null;
                    imageS = new ImageView(imagenRotate);
                    matrixC[i][j] = new Cuadro(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), null);
                    imageS.setImage(null); //rota la imagen 90 gredos sentido del reloj
                    imagenRotate = imageS.snapshot(snapshot, null);
                    imagenCambiada = new Imagen(matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth(), imagenRotate);
                    gc2.drawImage(this.imagenRotate, matrixC[i][j].getX(), matrixC[i][j].getY(), matrixC[i][j].getWidth(), matrixC[i][j].getHeigth());
                    System.out.println(matrixC[i][j].toString());
                }
            }
        }
    }

    EventHandler<ActionEvent> buttonDeleteAction = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            RemoveImage(x, y);
        }
    };

    /**
     * ******************GUARDAR LA IIMAGEN****************************
     */
    public void saveImage() throws IOException {
        FileChooser fileChooserS = new FileChooser();
        ExtensionFilter extFilter = new ExtensionFilter("PNG IMAGE (*.png)", "*.PNG", "(*.png)");
        fileChooserS.getExtensionFilters().addAll(extFilter);
        fileChooserS.setSelectedExtensionFilter(extFilter);
        File file = fileChooserS.showSaveDialog(null);
        scene.setFill(Color.TRANSPARENT); //le da el atributo de transparencia
        gc2.clearRect(0, 0, chunkWidth * rowsM, chunkHeight * colsM);
        for (int i = 0; i <= rowsM; i++) {
            for (int j = 0; j <= colsM; j++) {
                gc2.setStroke(Color.TRANSPARENT);
                gc2.strokeLine(0, i * chunkWidth, Integer.parseInt(field3.getText()), i * chunkWidth);
                gc2.strokeLine(j * chunkWidth, 0, j * chunkWidth, Integer.parseInt(field4.getText()));
            }
        }
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if (matrixC[i][j].getImage() != null) {
                    gc2.drawImage(matrixC[i][j].getImage().getImage(), matrixC[i][j].getX(), matrixC[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());
                    gc2.setStroke(Color.BLACK);
                }
            }
        }
        WritableImage imageMosaic = canvas2.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageMosaic, null);
        ImageIO.write(bImage, "*.png", file);
        gc2.clearRect(0, 0, chunkWidth * rowsM, chunkHeight * colsM);
        for (int i = 0; i <= rowsM; i++) {
            for (int j = 0; j <= colsM; j++) {
                gc2.strokeLine(0, i * chunkWidth, Integer.parseInt(field3.getText()), i * chunkWidth);
                gc2.strokeLine(j * chunkWidth, 0, j * chunkWidth, Integer.parseInt(field4.getText()));
            }
        }
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if (matrixC[i][j].getImage() != null) {
                    gc2.drawImage(matrixC[i][j].getImage().getImage(), matrixC[i][j].getX(), matrixC[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());
                }
            }
        }
    }

    EventHandler<ActionEvent> buttonSavePng = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            try {
                saveImage();
            } catch (IOException ex) {
                Logger.getLogger(ImageSelect.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

}
