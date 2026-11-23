package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

public class HabitacionFormController {
    @FXML
    private Label lblTitulo;
    @FXML private TextField txtNumero;
    @FXML private TextField txtPiso;
    @FXML private TextField txtPrecio;
    @FXML private ComboBox<String> cbTipo;
    @FXML private ComboBox<String> cbEstado;

    private Habitacion habitacion;
    private boolean saved = false;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("Individual", "Doble", "Matrimonial", "Familiar");
        cbEstado.getItems().addAll("Disponible", "Mantenimiento");
        cbEstado.setValue("Disponible");
    }

    public boolean isSaved() {
        return saved;
    }

    public Habitacion getHabitacion() {
        return habitacion;
    }

    public void setHabitacion(Habitacion h) {
        habitacion = h;
        if (h != null) {
            lblTitulo.setText("Editar habitación");
            txtNumero.setText(String.valueOf(h.getNumero_hab()));
            txtPiso.setText(String.valueOf(h.getPiso()));
            txtPrecio.setText(String.valueOf(h.getPrecio()));
            cbTipo.setValue(h.getTipo());
            cbEstado.setValue(h.getEstado());
        }
    }

    private boolean validate() {
        if (txtNumero.getText().trim().isEmpty() || txtPiso.getText().trim().isEmpty() ||
                txtPrecio.getText().trim().isEmpty() || cbTipo.getValue() == null || cbEstado.getValue() == null) {
            Alerts.viewAlerts("Datos obligatorios", "Complete todos los campos de la habitación.", "WARNING").showAndWait();
            return false;
        }
        try {
            int numero = Integer.parseInt(txtNumero.getText().trim());
            int piso = Integer.parseInt(txtPiso.getText().trim());
            double precio = Double.parseDouble(txtPrecio.getText().trim());
            if (numero <= 0 || piso <= 0 || precio <= 0) {
                Alerts.viewAlerts("Datos incorrectos", "Número, piso y precio deben ser mayores que cero.", "WARNING").showAndWait();
                return false;
            }
        } catch (NumberFormatException e) {
            Alerts.viewAlerts("Datos incorrectos", "Número, piso y precio deben ser valores numéricos.", "WARNING").showAndWait();
            return false;
        }
        return true;
    }

    @FXML
    private void handleSave() {
        if (!validate()) {
            return;
        }
        if (habitacion == null) {
            habitacion = new Habitacion();
        }
        habitacion.setNumero_hab(Integer.parseInt(txtNumero.getText().trim()));
        habitacion.setPiso(Integer.parseInt(txtPiso.getText().trim()));
        habitacion.setPrecio(Double.parseDouble(txtPrecio.getText().trim()));
        habitacion.setTipo(cbTipo.getValue());
        habitacion.setEstado(cbEstado.getValue());
        saved = true;
        cerrar();
    }

    @FXML
    private void handleCancel() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) txtNumero.getScene().getWindow();
        stage.close();
    }
}


