package com.mycompany.tareahilos;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TareaHilos extends Application {

    private int contadorClics = 0;

    @Override
    public void start(Stage primaryStage) {
        Label lblContar = new Label("Contar número de clics");
        TextField txtContador = new TextField("0");
        txtContador.setEditable(false);
        Button btnContar = new Button("¡Contar!");

        Label lblEsperar = new Label("Esperar 10 segundos");
        ProgressBar barraProgreso = new ProgressBar(0);
        Button btnEsperar = new Button("¡Esperar!");

        btnContar.setOnAction(e -> {
            contadorClics++;
            txtContador.setText(String.valueOf(contadorClics));
        });

        btnEsperar.setOnAction(e -> {
            btnEsperar.setDisable(true);
            barraProgreso.setProgress(0.0);

            Runnable miTareaEspera = new Runnable() {
                @Override
                public void run() {
                    try {
                        for (int i = 1; i <= 10; i++) {
                            Thread.sleep(1000);
                            final double avance = i / 10.0;
                            Platform.runLater(() -> {
                                barraProgreso.setProgress(avance);
                            });
                        }
                        Platform.runLater(() -> {
                            btnEsperar.setDisable(false);
                        });
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
            };

            Thread hiloEspera = new Thread(miTareaEspera);
            hiloEspera.start();
        });

        VBox root = new VBox(12, lblContar, txtContador, btnContar, lblEsperar, barraProgreso, btnEsperar);
        root.setStyle("-fx-padding: 20; -fx-alignment: center;");

        Scene scene = new Scene(root, 300, 320);
        primaryStage.setTitle("Conteo");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}