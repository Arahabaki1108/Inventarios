package com.arahabaki.inventarios;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class finanzasController {
    @FXML
    private TableView<Movimiento> tablaFinanzas;
    @FXML
    private TableColumn<Movimiento, Integer> colId;
    @FXML
    private TableColumn<Movimiento, String> colTipo;
    @FXML
    private TableColumn<Movimiento, Double> colMonto;
    @FXML
    private TableColumn<Movimiento, String> colFecha;
    @FXML
    private TableColumn<Movimiento, String> colDescripcion;
    @FXML
    private Button editarButton;
    @FXML
    private BarChart<String, Number> graficoFinanzas;

    @FXML private void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colMonto.setCellValueFactory(new PropertyValueFactory<>("monto"));
        colFecha.setCellValueFactory(new PropertyValueFactory<>("fecha"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<>("descripcion"));

        tablaFinanzas.setItems(obtenerMovimientos());

        editarButton.setOnAction(event -> abrirEdicion());
    }

    public void refrescarTabla() {
        tablaFinanzas.setItems(obtenerMovimientos());
        actualizarGrafico();
    }

    private ObservableList<Movimiento> obtenerMovimientos() {
        ObservableList<Movimiento> lista = FXCollections.observableArrayList();
        try (Connection conn = ConexionBD.conectar()) {
            String sql = "SELECT * FROM finanzas";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Movimiento m = new Movimiento(
                        rs.getInt("id_finanza"),
                        rs.getString("tipo"),
                        rs.getDouble("monto"),
                        rs.getString("fecha"),
                        rs.getString("descripcion")
                );
                lista.add(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private void abrirEdicion() {
        Movimiento seleccionado = tablaFinanzas.getSelectionModel().getSelectedItem();

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/arahabaki/inventarios/gestionarMovimientos.fxml"));
            Scene scene = new Scene(loader.load());
            Stage stage = new Stage();
            stage.setTitle("Gestionar movimientos");
            stage.setScene(scene);

            gestionarMovimientoController controller = loader.getController();
            controller.setMovimiento(seleccionado);
            controller.setFinanzasController(this);
            stage.show();

            tablaFinanzas.setItems(obtenerMovimientos());
            actualizarGrafico();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    private void actualizarGrafico() {
        double ingresos = 0;
        double egresos = 0;

        for (Movimiento m : tablaFinanzas.getItems()) {
            if (m.getTipo().equalsIgnoreCase("Ingreso")) {
                ingresos += m.getMonto();
            } else if (m.getTipo().equalsIgnoreCase("Egreso")) {
                egresos += m.getMonto();
            }
        }

        double ganancia = ingresos - egresos;

        graficoFinanzas.getData().clear();
        XYChart.Series<String, Number> serie = new XYChart.Series<>();
        serie.setName("Resultados");

        serie.getData().add(new XYChart.Data<>("Ingreso", ingresos));
        serie.getData().add(new XYChart.Data<>("Egreso", egresos));
        serie.getData().add(new XYChart.Data<>("Ganancia", ganancia));

        graficoFinanzas.getData().add(serie);
    }
}