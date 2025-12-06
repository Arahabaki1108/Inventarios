package com.arahabaki.inventarios;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class gestionarMovimientoController {
    @FXML private ComboBox<String> tipoBox;
    @FXML private TextField montoField;
    @FXML private DatePicker fechaPicker;
    @FXML private TextField descripcionField;
    @FXML private Button guardarButton;
    @FXML private Button eliminarButton;

    private Movimiento movimiento;

    public void setMovimiento(Movimiento movimiento) {
        this.movimiento = movimiento;
        if (movimiento != null) {
            tipoBox.setValue(movimiento.getTipo());
            montoField.setText(String.valueOf(movimiento.getMonto()));
            fechaPicker.setValue(java.time.LocalDate.parse(movimiento.getFecha()));
            descripcionField.setText(movimiento.getDescripcion());
        } else {
            eliminarButton.setDisable(true);
        }
    }

    private finanzasController finanzasController;
    public void setFinanzasController(finanzasController finanzasController) {
        this.finanzasController = finanzasController;
    }

    @FXML
    private void initialize() {
        tipoBox.getItems().addAll("Ingreso", "Egreso");
        guardarButton.setOnAction(e -> guardarMovimiento());
        eliminarButton.setOnAction(e -> eliminarMovimiento());
    }

    private void guardarMovimiento() {
        try (Connection conn = ConexionBD.conectar()) {
            if (movimiento == null) {
                String sql = "INSERT INTO finanzas (tipo, monto, fecha, descripcion) VALUES (?, ?, ?, ?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tipoBox.getValue());
                stmt.setDouble(2, Double.parseDouble(montoField.getText()));
                stmt.setString(3, fechaPicker.getValue().toString());
                stmt.setString(4, descripcionField.getText());
                stmt.executeUpdate();
                System.out.println("Movimiento agregado");
            } else {
                String sql = "UPDATE finanzas SET tipo=?, monto=?, fecha=?, descripcion=? WHERE id_finanza=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, tipoBox.getValue());
                stmt.setDouble(2, Double.parseDouble(montoField.getText()));
                stmt.setString(3, fechaPicker.getValue().toString());
                stmt.setString(4, descripcionField.getText());
                stmt.setInt(5, movimiento.getId());
                stmt.executeUpdate();
                System.out.println("Movimiento actualizado");
            }

            if (finanzasController != null) {
                finanzasController.refrescarTabla();
            }

            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void eliminarMovimiento() {
        if (movimiento != null) {
            try (Connection conn = ConexionBD.conectar()) {
                String sql = "DELETE FROM finanzas WHERE id_finanza=?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, movimiento.getId());
                stmt.executeUpdate();
                System.out.println("Movimiento eliminado");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if (finanzasController != null) {
            finanzasController.refrescarTabla();
        }

        Stage stage = (Stage) eliminarButton.getScene().getWindow();
        stage.close();
    }
}