package pe.edu.upeu.taller_git_gp1.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.HelloApplication;
import pe.edu.upeu.taller_git_gp1.implementacion.HabitacionDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IHabitacionDao;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

import java.util.Optional;

public class HabitacionController {
    @FXML
    private TableView<Habitacion> tableHabitacion;
    @FXML private TableColumn<Habitacion, Number> colId;
    @FXML private TableColumn<Habitacion, Number> colNumero;
    @FXML private TableColumn<Habitacion, Number> colPiso;
    @FXML private TableColumn<Habitacion, String> colTipo;
    @FXML private TableColumn<Habitacion, Number> colPrecio;
    @FXML private TableColumn<Habitacion, String> colEstado;
    @FXML private TextField txtBuscar;

    private final IHabitacionDao habitacionDao = new HabitacionDaoImpl();
    private ObservableList<Habitacion> listaHabitaciones;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colNumero.setCellValueFactory(cell -> cell.getValue().numero_habProperty());
        colPiso.setCellValueFactory(cell -> cell.getValue().pisoProperty());
        colTipo.setCellValueFactory(cell -> cell.getValue().tipoProperty());
        colPrecio.setCellValueFactory(cell -> cell.getValue().precioProperty());
        colEstado.setCellValueFactory(cell -> cell.getValue().estadoProperty());
        colPrecio.setCellFactory(col -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty || item == null ? null : String.format("S/ %.2f", item.doubleValue()));
            }
        });
        cargarDatos();
    }

    private void cargarDatos() {
        listaHabitaciones = FXCollections.observableArrayList(habitacionDao.listAll());
        tableHabitacion.setItems(listaHabitaciones);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Habitacion seleccionada = tableHabitacion.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alerts.viewAlerts("Selección requerida", "Seleccione una habitación para editar.", "WARNING").showAndWait();
            return;
        }
        showForm(habitacionDao.searchById(seleccionada.getId()));
    }

    @FXML
    private void handleDelete() {
        Habitacion seleccionada = tableHabitacion.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alerts.viewAlerts("Selección requerida", "Seleccione una habitación para eliminar.", "WARNING").showAndWait();
            return;
        }
        Alert confirmacion = Alerts.viewAlerts("Confirmar eliminación",
                "¿Está seguro de eliminar la habitación " + seleccionada.getNumero_hab() + "?", "CONFIRMATION");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            if (habitacionDao.delete(seleccionada)) {
                Alerts.viewAlerts("Correcto", "Habitación eliminada correctamente.", "INFORMATION").showAndWait();
                cargarDatos();
            } else {
                Alerts.viewAlerts("No se pudo eliminar", "La habitación puede tener reservas asociadas.", "WARNING").showAndWait();
            }
        }
    }

    @FXML
    private void handleSearch() {
        String numero = txtBuscar.getText().trim();
        if (numero.isEmpty()) {
            cargarDatos();
            return;
        }
        Habitacion habitacion = habitacionDao.searchByNumeroHab(numero);
        listaHabitaciones = FXCollections.observableArrayList();
        if (habitacion != null) {
            listaHabitaciones.add(habitacion);
        }
        tableHabitacion.setItems(listaHabitaciones);
    }

    @FXML
    private void handleClear() {
        txtBuscar.clear();
        cargarDatos();
    }

    private void showForm(Habitacion habitacion) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(
                    "/pe/edu/upeu/taller_git_gp1/views/habitacion-form.fxml"));
            Parent root = loader.load();
            HabitacionFormController controller = loader.getController();
            controller.setHabitacion(habitacion);

            Stage stage = new Stage();
            stage.setTitle(habitacion == null ? "Nueva habitación" : "Editar habitación");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tableHabitacion.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                Habitacion procesada = controller.getHabitacion();
                boolean resultado = habitacion == null ? habitacionDao.insert(procesada) : habitacionDao.update(procesada);
                if (resultado) {
                    Alerts.viewAlerts("Correcto", "Los datos de la habitación se guardaron correctamente.", "INFORMATION").showAndWait();
                } else {
                    Alerts.viewAlerts("Error", "No se pudieron guardar los datos. Verifique que el número no esté registrado.", "ERROR").showAndWait();
                }
                cargarDatos();
            }
        } catch (Exception e) {
            Alerts.viewAlerts("Error", "No se pudo abrir el formulario: " + e.getMessage(), "ERROR").showAndWait();
        }
    }
}

