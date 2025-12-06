package com.arahabaki.inventarios;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class gestionarProductoController {
    @FXML
    private TextField nombreField;
    @FXML
    private TextField categoriaField;
    @FXML
    private TextField precioField;
    @FXML
    private TextField costoField;
    @FXML
    private TextField stockField;
    @FXML
    private Button guardarButton;
    @FXML
    private Button eliminarButton;

    private Producto producto;

    public void setProducto(Producto producto){
        this.producto = producto;

        if(producto != null){
            nombreField.setText(producto.getNombre());
            categoriaField.setText(producto.getCategoria());
            precioField.setText(String.valueOf(producto.getPrecio()));
            costoField.setText(String.valueOf(producto.getCosto()));
            stockField.setText(String.valueOf(producto.getStock()));
        }
        else{
            eliminarButton.setDisable(true);
        }
    }
    @FXML
    private void initialize(){
        guardarButton.setOnAction(event -> guardarProducto());
        eliminarButton.setOnAction(event -> eliminarProducto());
    }

    private inventarioController inventarioController;

    public void setInventarioController(inventarioController inventarioController) {
        this.inventarioController = inventarioController;
    }

    private void guardarProducto() {
        try(Connection conn = ConexionBD.conectar()) {
            if (producto == null) {
                String sql = "INSERT INTO productos (nombre, categoria, precio_Venta, precio_Compra, stock_actual) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombreField.getText());
                stmt.setString(2, categoriaField.getText());
                stmt.setDouble(3, Double.parseDouble(precioField.getText()));
                stmt.setDouble(4, Double.parseDouble(costoField.getText()));
                stmt.setInt(5, Integer.parseInt(stockField.getText()));
                stmt.executeUpdate();
                System.out.print("Se ha agregado el producto");
            }
            else {
                String sql = "UPDATE productos SET nombre=?, categoria=?, precio_Venta=?, precio_Compra=?, stock_actual=? WHERE id_Producto=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, nombreField.getText());
                stmt.setString(2, categoriaField.getText());
                stmt.setDouble(3, Double.parseDouble(precioField.getText()));
                stmt.setDouble(4, Double.parseDouble(costoField.getText()));
                stmt.setInt(5, Integer.parseInt(stockField.getText()));
                stmt.setInt(6, producto.getId());
                stmt.executeUpdate();
                System.out.print("Se han actualizado los datos");
            }
            if(inventarioController != null) {
                inventarioController.refrescarTabla();
            }
            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {
        if(producto != null){
            try(Connection conn = ConexionBD.conectar()){
                String sql = "DELETE FROM productos WHERE id_Producto=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, producto.getId());
                stmt.executeUpdate();
                System.out.print("El producto ha sido eliminado");

                if(inventarioController != null) {
                    inventarioController.refrescarTabla();
                }
                Stage stage = (Stage) eliminarButton.getScene().getWindow();
                stage.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}
