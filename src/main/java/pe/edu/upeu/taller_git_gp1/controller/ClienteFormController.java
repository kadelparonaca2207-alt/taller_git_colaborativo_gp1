package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

public class ClienteFormController {
    @FXML
    private Label lblTitulo;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtDocumento;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;

    private Cliente cliente;
    private boolean saved = false;

    public boolean isSaved() {
        return saved;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente c) {
        cliente = c;
        if (c != null) {
            lblTitulo.setText("Editar cliente");
            txtNombre.setText(c.getNombre());
            txtApellido.setText(c.getApellido());
            txtDocumento.setText(String.valueOf(c.getNumero_documento()));
            txtCorreo.setText(c.getCorreo());
            txtTelefono.setText(c.getTelefono());
        }
    }

    private boolean validate() {
        if (txtNombre.getText().trim().isEmpty() || txtApellido.getText().trim().isEmpty() ||
                txtDocumento.getText().trim().isEmpty()) {
            Alerts.viewAlerts("Datos obligatorios", "Ingrese nombre, apellido y número de documento.", "WARNING").showAndWait();
            return false;
        }
        if (!txtDocumento.getText().trim().matches("[0-9]{8}")) {
            Alerts.viewAlerts("Documento incorrecto", "El documento debe contener 8 números.", "WARNING").showAndWait();
            return false;
        }
        if (!txtCorreo.getText().trim().isEmpty() && !txtCorreo.getText().trim().contains("@")) {
            Alerts.viewAlerts("Correo incorrecto", "Ingrese un correo electrónico válido.", "WARNING").showAndWait();
            return false;
        }
        return true;
    }

    private void cerrar() {
        Stage stage = (Stage) txtNombre.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSave() {
        if (!validate()) {
            return;
        }
        if (cliente == null) {
            cliente = new Cliente();
        }
        cliente.setNombre(txtNombre.getText().trim());
        cliente.setApellido(txtApellido.getText().trim());
        cliente.setNumero_documento(Integer.parseInt(txtDocumento.getText().trim()));
        cliente.setCorreo(txtCorreo.getText().trim());
        cliente.setTelefono(txtTelefono.getText().trim());
        saved = true;
        cerrar();
    }

    @FXML
    private void handleCancel() {
        cerrar();
    }
}


