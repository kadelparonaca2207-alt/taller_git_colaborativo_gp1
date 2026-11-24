package pe.edu.upeu.taller_git_gp1.utils;

import javafx.scene.control.Alert;

public class Alerts {
    public static Alert viewAlerts(String titulo, String mensaje, String tipo) {
        Alert alert;
        if ("WARNING".equalsIgnoreCase(tipo)) {
            alert = new Alert(Alert.AlertType.WARNING);
        } else if ("CONFIRMATION".equalsIgnoreCase(tipo)) {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
        } else if ("INFORMATION".equalsIgnoreCase(tipo)) {
            alert = new Alert(Alert.AlertType.INFORMATION);
        } else {
            alert = new Alert(Alert.AlertType.ERROR);
        }
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        return alert;
    }
}
