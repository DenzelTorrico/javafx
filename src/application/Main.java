package application;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.control.Label;
public class Main extends Application {

    private Pane selectedSection;

    @Override
    public void start(Stage primaryStage) {
        // Crear el header con el título y botones
        HBox header = new HBox(20);
        header.setPadding(new Insets(10));
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-background-color: #161618;");

        Label titleLabel = new Label("Mi App");
        titleLabel.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        Button configureWindowButton = new Button("Configurar Ventana");
        Button configureAlertButton = new Button("Configurar Alerta");

        header.getChildren().addAll(titleLabel, configureWindowButton, configureAlertButton);

        // Crear el GridPane principal para las secciones
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(20));
        grid.setVgap(10);
        grid.setHgap(5);
        grid.setAlignment(Pos.CENTER);
    
        
        VBox tablaAlertas = crearTablaAlertas();
        VBox tablaEventosAnteriores = crearTablaEventos("Eventos Anteriores");
        VBox tablaEventosSiguientes = crearTablaEventos("Eventos Siguientes");

        HBox tablasContainer = new HBox(20);
        tablasContainer.getChildren().addAll(tablaAlertas, tablaEventosAnteriores, tablaEventosSiguientes);
        tablasContainer.setAlignment(Pos.CENTER);
        
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPrefWidth(600);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPrefWidth(600);
        ColumnConstraints column3 = new ColumnConstraints();
        column3.setPrefWidth(600);
        ColumnConstraints column4 = new ColumnConstraints();
        column4.setPrefWidth(600);
        
        grid.getColumnConstraints().addAll(column1, column2, column3, column4);
        // Crear las secciones y botones
        for (int i = 1; i <= 8; i++) {
            StackPane sectionBox = new StackPane();
            sectionBox.setStyle("-fx-background-color: #cccccc; -fx-padding: 10;");
            //sectionBox.setMaxWidth(800);
            sectionBox.setMinSize(450, 80);
            sectionBox.setAlignment(Pos.CENTER);
            
            VBox content = new VBox(5);
            content.setAlignment(Pos.CENTER); // Alineación en la esquina superior izquierda

            Label sectionLabel = new Label("Section " + i);
            sectionLabel.setStyle("-fx-font-size: 18px;"); // Aumentar tamaño de la fuente
            Label alertLabel = new Label("Alert " + (char) (64 + i));

            content.getChildren().addAll(sectionLabel, alertLabel);

            Button colorButton = new Button("Cambiar Color");
            colorButton.setStyle("-fx-background-color: #009dad; -fx-text-fill: white;");
            colorButton.setPrefSize(100, 30);
            colorButton.setOnAction(e -> {
                selectedSection = sectionBox;
                showColorPickerModal(primaryStage);
            });

            StackPane.setAlignment(colorButton, Pos.TOP_RIGHT);
            sectionBox.getChildren().addAll(content, colorButton);

            grid.add(sectionBox, (i - 1) % 2, (i - 1) / 2);
            GridPane.setFillWidth(sectionBox, true);
        }


        VBox root = new VBox(20, header, grid, tablasContainer);

        root.setAlignment(Pos.TOP_CENTER);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 1000, 650);
        primaryStage.setScene(scene);
        //primaryStage.setMaximized(true); 
        primaryStage.setTitle("JavaFX Interface");
        primaryStage.show();
    }

    // Método para mostrar el modal del selector de color
    private void showColorPickerModal(Stage owner) {
        Stage modal = new Stage();
        modal.initModality(Modality.APPLICATION_MODAL);
        modal.initOwner(owner);

        // Crear botones de color
        HBox colorButtons = new HBox(10);
        colorButtons.setAlignment(Pos.CENTER);
        colorButtons.setPadding(new Insets(20));

        String[] colors = {"#FF0000", "#00FF00", "#0000FF", "#00FFFF", "#FF00FF", "#FFFF00",
                "#000000", "#FFFFFF", "#808080", "#FFA500", "#800080", "#FFC0CB"};
        String[] colorNames = {"Rojo", "Verde", "Azul", "Cian", "Magenta", "Amarillo",
                "Negro", "Blanco", "Gris", "Naranja", "Morado", "Rosa"};

        // Separar botones en 2 filas de 6 botones cada una
        VBox buttonRows = new VBox(10);
        buttonRows.setAlignment(Pos.CENTER);
        for (int i = 0; i < 2; i++) {
            HBox row = new HBox(10);
            row.setAlignment(Pos.CENTER);
            for (int j = 0; j < 6; j++) {
                int index = i * 6 + j;
                Button colorButton = createColorButton(colors[index], colorNames[index]);
                row.getChildren().add(colorButton);
            }
            buttonRows.getChildren().add(row);
        }

        Button closeButton = new Button("Cerrar");
        closeButton.setOnAction(e -> modal.close());

        VBox modalBox = new VBox(10, buttonRows, closeButton);
        modalBox.setAlignment(Pos.CENTER);
        modalBox.setPadding(new Insets(20));
        modalBox.setStyle("-fx-background-color: #444; -fx-border-radius: 10; -fx-background-radius: 10;");

        Scene modalScene = new Scene(modalBox, 600, 400);
        modal.setScene(modalScene);
        modal.setTitle("Seleccione un color");
        modal.showAndWait();
    }

    private Button createColorButton(String color, String colorName) {
        Button button = new Button(colorName);

        // Determinar si el color es claro u oscuro
        String textColor = isColorLight(color) ? "black" : "white";
        
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: " + textColor + ";");
        button.setShape(new Circle(25));
        button.setPrefSize(100, 100);

        // Añadir estilo hover
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: " + textColor + "; -fx-border-color: white; -fx-border-width: 2;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: " + textColor + ";"));

        // Cambiar color de fondo de la sección seleccionada
        button.setOnAction(e -> {
            selectedSection.setStyle("-fx-background-color: " + color + "; -fx-padding: 10;");
        });

        return button;
    }

    private boolean isColorLight(String color) {
        // Convertir el color hexadecimal a valores RGB
        Color fxColor = Color.web(color);
        double red = fxColor.getRed();
        double green = fxColor.getGreen();
        double blue = fxColor.getBlue();

        // Calcular la luminosidad relativa usando la fórmula estándar
        double luminosity = 0.2126 * red + 0.7152 * green + 0.0722 * blue;

        // Si la luminosidad es mayor que 0.5, el color es claro
        return luminosity > 0.5;
    }
    private VBox crearTablaAlertas() {
        TableView<Alerta> tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Alerta, Integer> colID = new TableColumn<>("ID");
        TableColumn<Alerta, String> colTipoVentana = new TableColumn<>("Tipo de Ventana");
        TableColumn<Alerta, String> colPrediccion = new TableColumn<>("Predicción");
        TableColumn<Alerta, String> colEvento = new TableColumn<>("Evento");
        TableColumn<Alerta, String> colCausa = new TableColumn<>("Causa");

        colID.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipoVentana.setCellValueFactory(new PropertyValueFactory<>("tipoVentana"));
        colPrediccion.setCellValueFactory(new PropertyValueFactory<>("prediccion"));
        colEvento.setCellValueFactory(new PropertyValueFactory<>("evento"));
        colCausa.setCellValueFactory(new PropertyValueFactory<>("causa"));

        tabla.getColumns().addAll(colID, colTipoVentana, colPrediccion, colEvento, colCausa);

        ObservableList<Alerta> alertas = FXCollections.observableArrayList(
            new Alerta(1, "Ventana A", "Alta", "Evento X", "Causa 1"),
            new Alerta(2, "Ventana B", "Media", "Evento Y", "Causa 2")
        );
        tabla.setItems(alertas);

        Label titulo = new Label("Alertas");
        titulo.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(titulo, tabla);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }
    private VBox crearTablaEventos(String titulo) {
        TableView<Evento> tabla = new TableView<>();
        tabla.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        TableColumn<Evento, Integer> colNum = new TableColumn<>("#");
        TableColumn<Evento, String> colFirst = new TableColumn<>("First");
        TableColumn<Evento, String> colLast = new TableColumn<>("Last");
        TableColumn<Evento, String> colHandle = new TableColumn<>("Handle");

        colNum.setCellValueFactory(new PropertyValueFactory<>("num"));
        colFirst.setCellValueFactory(new PropertyValueFactory<>("first"));
        colLast.setCellValueFactory(new PropertyValueFactory<>("last"));
        colHandle.setCellValueFactory(new PropertyValueFactory<>("handle"));

        tabla.getColumns().addAll(colNum, colFirst, colLast, colHandle);

        ObservableList<Evento> eventos = FXCollections.observableArrayList(
            new Evento(1, "Mark", "Otto", "@mdo"),
            new Evento(2, "Jacob", "Thornton", "@fat")
        );
        tabla.setItems(eventos);

        Label tituloLabel = new Label(titulo);
        tituloLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        VBox vbox = new VBox(10);
        vbox.getChildren().addAll(tituloLabel, tabla);
        vbox.setAlignment(Pos.CENTER);

        return vbox;
    }

    public static void main(String[] args) {
        launch(args);
    }
    public static class Alerta {
        private final int id;
        private final String tipoVentana;
        private final String prediccion;
        private final String evento;
        private final String causa;

        public Alerta(int id, String tipoVentana, String prediccion, String evento, String causa) {
            this.id = id;
            this.tipoVentana = tipoVentana;
            this.prediccion = prediccion;
            this.evento = evento;
            this.causa = causa;
        }

        // Getters
        public int getId() { return id; }
        public String getTipoVentana() { return tipoVentana; }
        public String getPrediccion() { return prediccion; }
        public String getEvento() { return evento; }
        public String getCausa() { return causa; }
    }

    public static class Evento {
        private final int num;
        private final String first;
        private final String last;
        private final String handle;

        public Evento(int num, String first, String last, String handle) {
            this.num = num;
            this.first = first;
            this.last = last;
            this.handle = handle;
        }

        // Getters
        public int getNum() { return num; }
        public String getFirst() { return first; }
        public String getLast() { return last; }
        public String getHandle() { return handle; }
    }
}
