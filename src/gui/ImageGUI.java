/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import data.ImageData;
import domain.Grid;
import domain.Picture;
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
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javax.imageio.ImageIO;

/**
 *
 * @author Heller
 */
public class ImageGUI extends Application {

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
    private Button buttonFlipH;
    private Button buttonFlipV;
    private Label label;
    private TextField field3;
    private Label label3;
    private TextField field4;
    private Label label4;
    private PixelReader pixel; //se encarga de leer pixel por pixel
    private WritableImage writable; //convierte pixeles en una imagen
    private ArrayList<Image> imagenPartes = new ArrayList<>();
    private ArrayList<Image> picture = new ArrayList<>();
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
    private Picture matrixP[][];
    private Grid matrixG[][];
    private int sizeMatrixI;
    private Picture imageSelect;
    private Picture imagenCambiada;
    private ImageView imageS;
    private Image imagenRotate;
    private int x;
    private int y;
    private int sizeMatrixC;
    private Image imageBackground;
    private BackgroundSize backgroundSize;
    private BackgroundImage backgroundImage;
    private Background background;
    private ImageData imageData = new ImageData();
    private MenuBar menuBar;
    private Menu file;
    private MenuItem menuItemOpen;
    private MenuItem menuItemSave;
    private File folderImage;
    private File folderGrid;
    private BorderPane root = new BorderPane();

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Mosaic Maker"); //Pone un titulo a la ventana
        initComponents(primaryStage); //inicializamos los componentes
        primaryStage.show(); //Mostramos la ventana
    }

    private void initComponents(Stage primaryStage) throws FileNotFoundException, IOException {
        this.button = new Button("Select an image");
        this.button.relocate(65, 605);
        this.button.setPrefSize(140, 30);
        this.button.setOnAction(buttonAction);
        this.label = new Label("Size");
        this.label.relocate(215, 610);
        this.label.setMinSize(150, 20);
        this.field = new TextField();
        this.field.relocate(240, 610);
        this.field.setPrefSize(70, 20);
        this.buttonSaveImage = new Button("Save image of mosaic");
        this.buttonSaveImage.relocate(339, 605);
        this.buttonSaveImage.setPrefSize(140, 30);
        this.buttonSaveImage.setOnAction(buttonSavePng);
        this.canvas2 = new Canvas(1400, 1400);
        canvas = new Canvas(1400, 1400);
        gc2 = this.canvas2.getGraphicsContext2D();
        this.label3 = new Label("Width");
        this.label3.relocate(580, 610);
        this.label3.setMinSize(150, 20);
        this.field3 = new TextField();
        this.field3.relocate(620, 610);
        this.field3.setPrefSize(70, 20);
        this.label4 = new Label("Heigth");
        this.label4.relocate(705, 610);
        this.label4.setMinSize(150, 20);
        this.field4 = new TextField();
        this.field4.relocate(750, 610);
        this.field4.setPrefSize(70, 20);
        this.buttonRotateL = new Button("Rotate left");
        this.buttonRotateL.relocate(830, 605);
        this.buttonRotateL.setPrefSize(80, 30);
        this.buttonRotateL.setOnAction(buttonRotateLAction);
        this.buttonRotateR = new Button("Rotate Right");
        this.buttonRotateR.relocate(920, 605);
        this.buttonRotateR.setPrefSize(90, 30);
        this.buttonRotateR.setOnAction(buttonRotateRAction);
        this.buttonFlipH = new Button("Flip Horizontal");
        this.buttonFlipH.relocate(1015, 605);
        this.buttonFlipH.setPrefSize(100, 30);
        this.buttonFlipH.setOnAction(buttonFlipHAction);
        this.buttonFlipV = new Button("Flip Vertical");
        this.buttonFlipV.relocate(1125, 605);
        this.buttonFlipV.setPrefSize(90, 30);
        this.buttonFlipV.setOnAction(buttonFlipVAction);
        this.buttonDeleted = new Button("Deleted");
        this.buttonDeleted.relocate(1225, 605);
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
        //this.pane.getChildren().add(this.buttonSave);
        this.pane.getChildren().add(this.buttonFlipH);
        this.pane.getChildren().add(this.buttonFlipV);
        this.scene = new Scene(this.pane, WIDTH, HEIGHT);
        menuBar = new MenuBar();
        file = new Menu("File");
        menuItemOpen = new MenuItem("Open project");
        menuItemSave = new MenuItem("Save project");
        menuItemSave.setOnAction(ItemSaveProject);
        menuItemOpen.setOnAction(ItemOpenProject);
        menuBar.getMenus().add(file);
        file.getItems().addAll(menuItemOpen);
        file.getItems().addAll(menuItemSave);
        root.setTop(menuBar);
        this.scene = new Scene(this.root, WIDTH, HEIGHT);
        pane.setLayoutX(0);
        pane.setLayoutY(20);
        root.getChildren().add(pane);
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
                Logger.getLogger(ImageGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ImageGUI.class.getName()).log(Level.SEVERE, null, ex);
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
        matrixP = new Picture[sizeMatrixI][sizeMatrixI];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                this.pixel = image.getPixelReader();//recibe los pixeles de la imagen
                this.writable = new WritableImage(this.pixel, x * (int) chunkWidth, y * (int) chunkHeight, (int) chunkWidth, (int) chunkHeight);
                System.out.println("imagen " + writable.getPixelWriter());
                imagenPartes.add(writable);
            }
        }
        rowsM = (int) (Integer.parseInt(field3.getText()) / chunkWidth);
        colsM = (int) (Integer.parseInt(field4.getText()) / chunkWidth);
        sizeMatrixC = rowsM * colsM;
        canvas2.setWidth(chunkWidth * rowsM);
        canvas2.setHeight(chunkHeight * colsM);
        matrixG = new Grid[sizeMatrixC][sizeMatrixC];
        for (int i = 0; i <= rowsM; i++) {
            for (int j = 0; j <= colsM; j++) {
                gc2.strokeLine(0, i * chunkWidth, Integer.parseInt(field3.getText()), i * chunkWidth);
                gc2.strokeLine(j * chunkWidth, 0, j * chunkWidth, Integer.parseInt(field4.getText()));
                matrixG[i][j] = new Grid(i * chunkWidth, j * chunkHeight, chunkWidth, chunkWidth, null, null);
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
                matrixP[i][j] = new Picture(i * (chunkWidth), j * (chunkHeight), chunkWidth, chunkHeight, imagenPartes.get(iter));
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
                if ((x >= matrixP[i][j].getX() && x <= matrixP[i][j].getX() + matrixP[i][j].getWidth())
                        && (y >= matrixP[i][j].getY() && y <= matrixP[i][j].getY() + matrixP[i][j].getHeigth())) {
                    imageSelect = new Picture(matrixP[i][j].getX(), matrixP[i][j].getY(), chunkWidth, chunkWidth, matrixP[i][j].getImage());
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
                if ((x >= matrixG[i][j].getX() && x <= matrixG[i][j].getX() + matrixG[i][j].getWidth())
                        && (y >= matrixG[i][j].getY() && y <= matrixG[i][j].getY() + matrixG[i][j].getHeigth())) {
                    if (matrixG[i][j].getImage() == null) {
                        gc2.drawImage(imageSelect.getImage(), matrixG[i][j].getX(), matrixG[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());
                        imageSelect = new Picture(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imageSelect.getImage());
                        matrixG[i][j] = new Grid(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imageSelect);
                    }
                }
            }
        }
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                System.out.println(matrixG[i][j].toString());
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
                if ((x >= matrixG[i][j].getX() && x <= matrixG[i][j].getX() + matrixG[i][j].getWidth())
                        && (y >= matrixG[i][j].getY() && y <= matrixG[i][j].getY() + matrixG[i][j].getHeigth())) {
                    imagenRotate = matrixG[i][j].getImage().getImage();
                    imageS = new ImageView(imagenRotate);
                    imageS.setRotate(imageS.getRotate() + 90); //rota la imagen 90 gredos sentido del reloj
                    imagenRotate = imageS.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
                    gc2.drawImage(this.imagenRotate, matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth());
                    imagenRotate = imageS.snapshot(this.snapshot, null);
                    imagenCambiada = new Picture(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenRotate);
                    matrixG[i][j] = new Grid(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenCambiada);
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
                if ((x >= matrixG[i][j].getX() && x <= matrixG[i][j].getX() + matrixG[i][j].getWidth())
                        && (y >= matrixG[i][j].getY() && y <= matrixG[i][j].getY() + matrixG[i][j].getHeigth())) {
                    imagenRotate = matrixG[i][j].getImage().getImage();
                    imageS = new ImageView(imagenRotate);
                    imageS.setRotate(imageS.getRotate() - 90); //rota la imagen 90 gredos sentido del reloj
                    imagenRotate = imageS.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
                    gc2.drawImage(this.imagenRotate, matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth());
                    imagenRotate = imageS.snapshot(this.snapshot, null);
                    imagenCambiada = new Picture(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenRotate);
                    matrixG[i][j] = new Grid(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenCambiada);
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
                if ((x >= matrixG[i][j].getX() && x <= matrixG[i][j].getX() + matrixG[i][j].getWidth())
                        && (y >= matrixG[i][j].getY() && y <= matrixG[i][j].getY() + matrixG[i][j].getHeigth())) {
                    imagenRotate = null;
                    imageS = new ImageView(imagenRotate);
                    matrixG[i][j] = new Grid(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), null, null);
                    imageS.setImage(null);
                    imagenRotate = imageS.snapshot(snapshot, null);
                    imagenCambiada = new Picture(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenRotate);
                    gc2.drawImage(this.imagenRotate, matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth());
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
    public void saveImage(File file) throws IOException {
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
                if (matrixG[i][j].getImage() != null) {
                    gc2.drawImage(matrixG[i][j].getImage().getImage(), matrixG[i][j].getX(), matrixG[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());
                    gc2.setStroke(Color.BLACK);
                }
            }
        }
        WritableImage imageMosaic = canvas2.snapshot(null, null);
        BufferedImage bImage = SwingFXUtils.fromFXImage(imageMosaic, null);
        ImageIO.write(bImage, "png", new File(file.getPath() + ".png"));
        gc2.clearRect(0, 0, chunkWidth * rowsM, chunkHeight * colsM);
        for (int i = 0; i <= rowsM; i++) {
            for (int j = 0; j <= colsM; j++) {
                gc2.strokeLine(0, i * chunkWidth, Integer.parseInt(field3.getText()), i * chunkWidth);
                gc2.strokeLine(j * chunkWidth, 0, j * chunkWidth, Integer.parseInt(field4.getText()));
            }
        }
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if (matrixG[i][j].getImage() != null) {
                    gc2.drawImage(matrixG[i][j].getImage().getImage(), matrixG[i][j].getX(), matrixG[i][j].getY(), imageSelect.getWidth(), imageSelect.getWidth());
                }
            }
        }
    }

    EventHandler<ActionEvent> buttonSavePng = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            try {
                FileChooser fileChooserS = new FileChooser();
                ExtensionFilter extFilter = new ExtensionFilter("PNG IMAGE (*.png)", "*.PNG", "(*.png)");
                File file = fileChooserS.showSaveDialog(null);
                fileChooserS.setSelectedExtensionFilter(extFilter);
                fileChooserS.getExtensionFilters().addAll(extFilter);
                saveImage(file);
            } catch (IOException ex) {
                Logger.getLogger(ImageGUI.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    /**
     * ***********METODO EN EL QUE VOLTEA A LA HORIZONTAL**************
     */
    public void flipHorizontalImage(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrixG[i][j].getX() && x <= matrixG[i][j].getX() + matrixG[i][j].getWidth())
                        && (y >= matrixG[i][j].getY() && y <= matrixG[i][j].getY() + matrixG[i][j].getHeigth())) {
                    imagenRotate = matrixG[i][j].getImage().getImage();
                    imageS = new ImageView(imagenRotate);
                    imageS.setScaleX(-1.0);
                    imagenRotate = imageS.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
                    gc2.drawImage(this.imagenRotate, matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth());
                    imagenCambiada = new Picture(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenRotate);
                    matrixG[i][j] = new Grid(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenCambiada);
                }
            }
        }
    }
    EventHandler<ActionEvent> buttonFlipHAction = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            flipHorizontalImage(x, y);
        }
    };

    /**
     * ***********METODO EN EL QUE VOLTEA A LA VERTICAL**************
     */
    public void flipVerticalImage(int x, int y) {
        for (int i = 0; i < rowsM; i++) {
            for (int j = 0; j < colsM; j++) {
                if ((x >= matrixG[i][j].getX() && x <= matrixG[i][j].getX() + matrixG[i][j].getWidth())
                        && (y >= matrixG[i][j].getY() && y <= matrixG[i][j].getY() + matrixG[i][j].getHeigth())) {
                    imagenRotate = matrixG[i][j].getImage().getImage();
                    imageS = new ImageView(imagenRotate);
                    imageS.setScaleY(-1.0);
                    imagenRotate = imageS.snapshot(snapshot, null); //obtienen la imagen modificada y la sobreescribe con la original
                    gc2.drawImage(this.imagenRotate, matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth());
                    imagenCambiada = new Picture(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenRotate);
                    matrixG[i][j] = new Grid(matrixG[i][j].getX(), matrixG[i][j].getY(), matrixG[i][j].getWidth(), matrixG[i][j].getHeigth(), imagenCambiada);
                }
            }
        }
    }
    /**
     * *******EVENTO DEL MOUSE PARA EL FLIP VERTICAL*************
     */
    EventHandler<ActionEvent> buttonFlipVAction = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            flipVerticalImage(x, y);
        }
    };

    /**
     * ***METODO EN EL QUE SE GUARDA TODO EL PROYECTO PARA LUEGO USARLO****
     */
    public void saveProject() throws IOException, ClassNotFoundException {        
        Picture matrixPicSave[][]=new Picture[sizeMatrixI][sizeMatrixI];
        Grid matrixGridSave[][]=new Grid[sizeMatrixC][sizeMatrixC];
        Picture pictureSave;
        Grid gridSave;
        FileChooser fileChooserS = new FileChooser();
        File file = fileChooserS.showSaveDialog(null);
        String pathFolder = file.getPath() + "\\Project";
        File folder = new File(pathFolder);
        folder.mkdirs(); // esto crea la carpeta java, y requiere que exista la ruta
        folderImage = new File(folder.getPath() + "//folderImage");
        folderImage.mkdirs();
        folderGrid = new File(folder.getPath() + "//folderGrid");
        folderGrid.mkdirs();
        int contPictures = 0;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                try {
                    BufferedImage bImage = SwingFXUtils.fromFXImage(matrixP[x][y].getImage(), null);
                    ImageIO.write(bImage, "jpg", new File(folder.getPath() + "//folderImage" + "//img" + contPictures + ".jpg"));
                    String pathPicture = folder.getPath() + "//folderImage" + "//img" + contPictures + ".jpg";
                    pictureSave = new Picture(matrixP[x][y].getX(), matrixP[x][y].getY(), matrixP[x][y].getWidth(), matrixP[x][y].getHeigth(), pathPicture);
                    contPictures++;
                    matrixPicSave[x][y] = pictureSave;
                } catch (IOException ex) {
                    Logger.getLogger(ImageGUI.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        imageData.savePicture(matrixPicSave, folder, rows, cols);
        int cont = 0;
        for (int x = 0; x <= rowsM; x++) {
            for (int y = 0; y <= colsM; y++) {
                if (matrixG[x][y].getImage() != null) {
                    try {
                        BufferedImage bImage = SwingFXUtils.fromFXImage(matrixG[x][y].getImage().getImage(), null);
                        ImageIO.write(bImage, "jpg", new File(folder.getPath() + "//folderGrid" + "//imgG" + cont + ".jpg"));
                        String pathGrid = folder.getPath() + "//folderImage" + "//img" + cont + ".jpg";
                        gridSave = new Grid(matrixG[x][y].getX(), matrixG[x][y].getY(), matrixG[x][y].getWidth(), matrixG[x][y].getHeigth(), pathGrid);
                        matrixGridSave[x][y] = gridSave;
                        cont++;
                    } catch (IOException ex) {
                        Logger.getLogger(ImageGUI.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }
        imageData.saveG(matrixGridSave, folder, rowsM, colsM);
    }
    EventHandler<ActionEvent> ItemSaveProject = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            try {
                saveProject();
            } catch (IOException ex) {
                Logger.getLogger(ImageGUI.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ImageGUI.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };

    EventHandler<ActionEvent> ItemOpenProject = new EventHandler<ActionEvent>() {
        public void handle(ActionEvent event) {
            FileChooser fileChooserO = new FileChooser();
            File fileN = fileChooserO.showOpenDialog(null);
            String pathProject = fileN.getPath();
            String pathj = ".//Project//folderImage";

        }
    };
}
