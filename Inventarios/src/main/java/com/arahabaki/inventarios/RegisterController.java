package com.arahabaki.inventarios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private PasswordField passwordField;

    @FXML
    private TextField nombreTextField;

    @FXML
    private TextField rolTextField;

    @FXML
    private Button registroButton;

    @FXML
    public void initialize(){
        registroButton.setOnAction(event -> registrarUsuario());
    }

    public void registrarUsuario(){
        String nombre = nombreTextField.getText();
        String contraseña = passwordField.getText();
        String rol = rolTextField.getText();

        if (nombre.isEmpty() || contraseña.isEmpty() || rol.isEmpty()){
            System.out.print("Por favor, complete todos los campos");
        }
        try(Connection conn = ConexionBD.conectar()){
            String sql = "INSERT INTO usuarios (nombre, contraseña, rol) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, rol);
            stmt.setString(2, contraseña);
            stmt.setString(3, nombre);

            int filas = stmt.executeUpdate();

            if(filas > 0){
                System.out.print("Se ha registrado de forma exitosa");
            }
            else{
                System.out.print("No se registró el usuario");
            }
        }
        catch (SQLException e) {
            System.out.print("Error, no se registró el usuario");
            e.printStackTrace();
        }
    }
}
