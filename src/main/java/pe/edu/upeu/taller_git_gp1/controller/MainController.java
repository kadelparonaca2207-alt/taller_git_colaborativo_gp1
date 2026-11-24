package pe.edu.upeu.taller_git_gp1.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import pe.edu.upeu.taller_git_gp1.HelloApplication;
import pe.edu.upeu.taller_git_gp1.modelo.UserSession;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

import java.io.IOException;

public class MainController {
    @FXML
    private StackPane contentArea;

    @FXML
    private Label lblUsuario;

    @FXML
    private void handleLogout() {
        UserSession.getInstance().cleanSession();
        System.exit(0);
    }

    @FXML
    public void initialize() {
        if (UserSession.getInstance().getUserLogger() != null) {
            lblUsuario.setText("Bienvenido, " + UserSession.getInstance().getUserLogger().getUser());
        }
        showHome();
    }

    @FXML
    private void showHome() {
        loadView("home-view.fxml");
    }

    @FXML
    private void loadView(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(
                    "/pe/edu/upeu/taller_git_gp1/views/" + fxml));

            Parent view = loader.load();
            contentArea.getChildren().setAll(view);

        } catch (IOException | RuntimeException e) {
            Alerts.viewAlerts("Error", "No se pudo cargar la vista " + fxml + ": " + e.getMessage(), "ERROR").showAndWait();
        }
    }

    @FXML
    private void showCliente(){
        loadView("cliente-view.fxml");
    }

    @FXML
    private void showHabitaciones(){
        loadView("habitacion-view.fxml");
    }
    @FXML
    private void showReservas() {
        loadView("reserva-view.fxml");
    }
}
