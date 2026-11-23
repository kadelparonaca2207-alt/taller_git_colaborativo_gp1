package pe.edu.upeu.taller_git_gp1.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.HelloApplication;
import pe.edu.upeu.taller_git_gp1.implementacion.ReservaDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IReservaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;
import pe.edu.upeu.taller_git_gp1.modelo.Reserva;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

import java.util.Optional;

public class ReservaController {
    @FXML
    private TableView<Reserva> tableReserva;
    @FXML private TableColumn<Reserva, Number> colId;
    @FXML private TableColumn<Reserva, String> colCliente;
    @FXML private TableColumn<Reserva, String> colDocumento;
    @FXML private TableColumn<Reserva, String> colHabitacion;
    @FXML private TableColumn<Reserva, String> colTipo;
    @FXML private TableColumn<Reserva, String> colEntrada;
    @FXML private TableColumn<Reserva, String> colSalida;
    @FXML private TableColumn<Reserva, String> colEstado;

    private final IReservaDao reservaDao = new ReservaDaoImpl();
    private ObservableList<Reserva> listaReservas;

    @FXML
    public void initialize() {
        colId.setCellValueFactory(cell -> cell.getValue().idProperty());
        colCliente.setCellValueFactory(cell -> {
            Cliente c = cell.getValue().getCliente();
            return new SimpleStringProperty(c == null ? "" : c.getNombre() + " " + c.getApellido());
        });
        colDocumento.setCellValueFactory(cell -> {
            Cliente c = cell.getValue().getCliente();
            return new SimpleStringProperty(c == null ? "" : String.valueOf(c.getNumero_documento()));
        });
        colHabitacion.setCellValueFactory(cell -> {
            Habitacion h = cell.getValue().getHabitacion();
            return new SimpleStringProperty(h == null ? "" : String.valueOf(h.getNumero_hab()));
        });
        colTipo.setCellValueFactory(cell -> {
            Habitacion h = cell.getValue().getHabitacion();
            return new SimpleStringProperty(h == null ? "" : h.getTipo());
        });
        colEntrada.setCellValueFactory(cell -> cell.getValue().fecha_entradaProperty());
        colSalida.setCellValueFactory(cell -> cell.getValue().fecha_salidaProperty());
        colEstado.setCellValueFactory(cell -> cell.getValue().estadoProperty());
        cargarDatos();
    }

    private void cargarDatos() {
        listaReservas = FXCollections.observableArrayList(reservaDao.listAll());
        tableReserva.setItems(listaReservas);
    }

    @FXML
    private void handleNew() {
        showForm(null);
    }

    @FXML
    private void handleEdit() {
        Reserva seleccionada = tableReserva.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alerts.viewAlerts("Selección requerida", "Seleccione una reserva para editar.", "WARNING").showAndWait();
            return;
        }
        showForm(reservaDao.searchById(seleccionada.getId()));
    }

    @FXML
    private void handleDelete() {
        Reserva seleccionada = tableReserva.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            Alerts.viewAlerts("Selección requerida", "Seleccione una reserva para eliminar.", "WARNING").showAndWait();
            return;
        }
        Alert confirmacion = Alerts.viewAlerts("Confirmar eliminación",
                "¿Está seguro de eliminar la reserva N.° " + seleccionada.getId() + "?", "CONFIRMATION");
        Optional<ButtonType> respuesta = confirmacion.showAndWait();
        if (respuesta.isPresent() && respuesta.get() == ButtonType.OK) {
            if (reservaDao.delete(seleccionada)) {
                Alerts.viewAlerts("Correcto", "Reserva eliminada correctamente.", "INFORMATION").showAndWait();
                cargarDatos();
            } else {
                Alerts.viewAlerts("Error", "No se pudo eliminar la reserva.", "ERROR").showAndWait();
            }
        }
    }

    @FXML
    private void handleRefresh() {
        cargarDatos();
    }

    private void showForm(Reserva reserva) {
        try {
            FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource(
                    "/pe/edu/upeu/taller_git_gp1/views/reserva-form.fxml"));
            Parent root = loader.load();
            ReservaFormController controller = loader.getController();
            controller.setReserva(reserva);

            Stage stage = new Stage();
            stage.setTitle(reserva == null ? "Nueva reserva" : "Editar reserva");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(tableReserva.getScene().getWindow());
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isSaved()) {
                Reserva procesada = controller.getReserva();
                boolean resultado = reserva == null ? reservaDao.insert(procesada) : reservaDao.update(procesada);
                if (resultado) {
                    Alerts.viewAlerts("Correcto", "La reserva se guardó correctamente.", "INFORMATION").showAndWait();
                } else {
                    Alerts.viewAlerts("No se pudo guardar", "La habitación no está disponible para esas fechas o los datos no son válidos.", "WARNING").showAndWait();
                }
                cargarDatos();
            }
        } catch (Exception e) {
            Alerts.viewAlerts("Error", "No se pudo abrir el formulario: " + e.getMessage(), "ERROR").showAndWait();
        }
    }
}


