package com.arahabaki.inventarios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class menuController {
    @FXML
    private Button opcionesButton;

    @FXML
    private Button inventarioButton;

    @FXML
    private Button finanzasButton;

    @FXML
    private Button resumenButton;

    @FXML
    private void initialize(){
        opcionesButton.setOnAction(event -> abrirOpciones());
        inventarioButton.setOnAction(event -> abrirInventario());
        finanzasButton.setOnAction(event -> abrirFinanzas());
        resumenButton.setOnAction(event -> abrirResumen());
    }

    private void abrirOpciones(){

    }

    private void abrirInventario() {
        try {
            Stage actualstage = (Stage) inventarioButton.getScene().getWindow();
            actualstage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/inventario.fxml"));
            Scene scene = new Scene(loader.load());
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Stage stage = new Stage();
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            stage.setTitle("Inventario");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirFinanzas() {
        try {
            Stage actualstage = (Stage) finanzasButton.getScene().getWindow();
            actualstage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/finanzas.fxml"));
            Scene scene = new Scene(loader.load());
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Stage stage = new Stage();
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            stage.setTitle("Finanzas");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void abrirResumen() {
        try {
            Stage actualstage = (Stage) resumenButton.getScene().getWindow();
            actualstage.close();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/resumen.fxml"));
            Scene scene = new Scene(loader.load());
            javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getBounds();
            Stage stage = new Stage();
            stage.setWidth(screenBounds.getWidth());
            stage.setHeight(screenBounds.getHeight());
            stage.setTitle("Resumen");
            stage.setScene(scene);
            stage.show();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

}
