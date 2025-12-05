package com.arahabaki.inventarios;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button registrarseButton;

    @FXML
    private Button ingresarButton;

    @FXML
    public void initialize (){
        ingresarButton.setOnAction(event ->validarLogin());
        registrarseButton.setOnAction(event ->abrirRegistro());
    }

    private void validarLogin() {
        String nombre = usuarioTextField.getText();
        String contraseña = passwordField.getText();

        if (nombre.isEmpty() || contraseña.isEmpty()){
            System.out.print("Por favor ingrese su usuario y contraseña.");
            return;
        }
        try (Connection conn = ConexionBD.conectar()){
            String sql = "SELECT * FROM usuarios WHERE nombre = ? AND contraseña = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, nombre);
            stmt.setString(2, contraseña);

            ResultSet rs = stmt.executeQuery();

            if(rs.next()){
                System.out.print("Bienvenido");
                Stage actualstage = (Stage) ingresarButton.getScene().getWindow();
                actualstage.close();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/menuPrincipal.fxml"));
                Scene scene = new Scene(loader.load());
                javafx.geometry.Rectangle2D screenBounds = Screen.getPrimary().getBounds();
                Stage stage = new Stage();
                stage.setWidth(screenBounds.getWidth());
                stage.setHeight(screenBounds.getHeight());
                stage.setTitle("Menú principal");
                stage.setScene(scene);
                stage.show();
            }
            else{
                System.out.print("Por favor intentelo de nuevo.");
            }
        }
        catch (SQLException e){
            System.out.print("No se pudo conectar con la base de datos");
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void abrirRegistro(){
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/registro.fxml"));
            Scene scene = new Scene(loader.load(), 520, 420);
            Stage stage = new Stage();
            stage.setTitle("Registro de usuario");
            stage.setScene(scene);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
