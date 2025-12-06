package com.arahabaki.inventarios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class inventarioController {
    @FXML
    private TableView<Producto> tablaInventario;

    @FXML
    private TableColumn<Producto, Integer> colId;

    @FXML
    private TableColumn<Producto, String> colNombre;

    @FXML
    private TableColumn<Producto, String> colCategoria;

    @FXML
    private TableColumn<Producto, String> colPrecio;

    @FXML
    private TableColumn<Producto, String> colCosto;

    @FXML
    private TableColumn<Producto, String> colStock;
    
    @FXML
    private Button editarButton;

    @FXML
    private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        colPrecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
        colCosto.setCellValueFactory(new PropertyValueFactory<>("costo"));
        colStock.setCellValueFactory(new PropertyValueFactory<>("stock"));
        tablaInventario.setItems(obtenerProductos());

        editarButton.setOnAction(event -> abrirEdicion());
    }

    private ObservableList<Producto> obtenerProductos(){
        ObservableList<Producto> lista = FXCollections.observableArrayList();

        try(Connection conn = ConexionBD.conectar()){
            String sql = "SELECT * FROM productos";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                Producto p = new Producto(
                    rs.getInt("id_Producto"),
                    rs.getString("nombre"),
                    rs.getString("categoria"),
                    rs.getDouble("precio_Venta"),
                    rs.getDouble("precio_Compra"),
                    rs.getInt("stock_actual")
                );
                    lista.add(p);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return lista;
    }

    public void refrescarTabla() {
        tablaInventario.setItems(obtenerProductos());
    }


    private void abrirEdicion() {
        Producto seleccionado = tablaInventario.getSelectionModel().getSelectedItem();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/gestionarProducto.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestionar producto");
            stage.setScene(scene);

            gestionarProductoController controller = loader.getController();
            controller.setProducto(seleccionado);
            controller.setInventarioController(this);
            stage.show();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
