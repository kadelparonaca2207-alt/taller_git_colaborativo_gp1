package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.implementacion.UsuarioDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IUsuarioDao;
import pe.edu.upeu.taller_git_gp1.modelo.UserSession;
import pe.edu.upeu.taller_git_gp1.modelo.Usuario;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

import java.io.IOException;

public class UsuarioController {
    @FXML
    private TextField txUser;
    @FXML
    private PasswordField txPass;

    private final IUsuarioDao usuarioDao = new UsuarioDaoImpl();

    @FXML
    private void handleLogin() {
        String user = txUser.getText();
        String password = txPass.getText();
        if (user.isEmpty() || password.isEmpty()) {
            Alert alert = Alerts.viewAlerts("Campos vacíos", "Ingrese usuario y contraseña", "WARNING");
            alert.showAndWait();
            return;
        }
        Usuario u = usuarioDao.login(user, password);
        if (u != null) {
            Alert alert = Alerts.viewAlerts("Acceso autorizado", "Bienvenido/a", "INFORMATION");
            alert.showAndWait();
            UserSession.getInstance().setUserLogger(u);
            navigateToHome();
        } else {
            Alert alert = Alerts.viewAlerts("Acceso denegado", "Credenciales inválidas", "ERROR");
            alert.showAndWait();
        }
    }

    private void navigateToHome() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/pe/edu/upeu/taller_git_gp1/views/main-view.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = (Stage) txUser.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setMaximized(true);
            stage.setTitle("Hotel KAS - Sistema de reservas");
            stage.show();
        } catch (IOException e) {
            Alerts.viewAlerts("Error", "No se pudo abrir la ventana principal: " + e.getMessage(), "ERROR").showAndWait();
        }
    }
}
