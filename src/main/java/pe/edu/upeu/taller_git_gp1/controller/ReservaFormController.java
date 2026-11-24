package pe.edu.upeu.taller_git_gp1.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.implementacion.HabitacionDaoImpl;
import pe.edu.upeu.taller_git_gp1.implementacion.ReservaDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.interfaces.IHabitacionDao;
import pe.edu.upeu.taller_git_gp1.interfaces.IReservaDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.modelo.Habitacion;
import pe.edu.upeu.taller_git_gp1.modelo.Reserva;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class ReservaFormController {
    @FXML private Label lblTitulo;
    @FXML private Label lblTotal;
    @FXML private ComboBox<String> cbCliente;
    @FXML private ComboBox<String> cbHabitacion;
    @FXML private DatePicker dpFechaEntrada;
    @FXML private DatePicker dpFechaSalida;
    @FXML private ComboBox<String> cbEstado;

    private final IClienteDao clienteDao = new ClienteDaoImpl();
    private final IHabitacionDao habitacionDao = new HabitacionDaoImpl();
    private final IReservaDao reservaDao = new ReservaDaoImpl();
    private List<Cliente> clientes;
    private List<Habitacion> habitaciones;
    private Reserva reserva;
    private boolean saved = false;

    @FXML
    public void initialize() {
        clientes = clienteDao.listAll();
        habitaciones = habitacionDao.listAll();

        cbCliente.setItems(FXCollections.observableArrayList());
        for (Cliente cliente : clientes) {
            cbCliente.getItems().add(cliente.getNumero_documento() + " - " + cliente.getNombre() + " " + cliente.getApellido());
        }

        cbHabitacion.setItems(FXCollections.observableArrayList());
        for (Habitacion habitacion : habitaciones) {
            String estado = "Mantenimiento".equalsIgnoreCase(habitacion.getEstado()) ? " (Mantenimiento)" : "";
            cbHabitacion.getItems().add("N.° " + habitacion.getNumero_hab() + " - " + habitacion.getTipo() +
                    " - S/ " + String.format("%.2f", habitacion.getPrecio()) + estado);
        }

        cbEstado.getItems().addAll("Pendiente", "Confirmada", "Cancelada");
        cbEstado.setValue("Pendiente");
        cbHabitacion.setOnAction(e -> calcularTotal());
        dpFechaEntrada.valueProperty().addListener((obs, anterior, nuevo) -> calcularTotal());
        dpFechaSalida.valueProperty().addListener((obs, anterior, nuevo) -> calcularTotal());
        calcularTotal();
    }

    public boolean isSaved() {
        return saved;
    }

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva r) {
        reserva = r;
        if (r != null) {
            lblTitulo.setText("Editar reserva");
            for (int i = 0; i < clientes.size(); i++) {
                if (clientes.get(i).getId() == r.getCliente_id()) {
                    cbCliente.getSelectionModel().select(i);
                    break;
                }
            }
            for (int i = 0; i < habitaciones.size(); i++) {
                if (habitaciones.get(i).getId() == r.getHabitacion_id()) {
                    cbHabitacion.getSelectionModel().select(i);
                    break;
                }
            }
            if (r.getFecha_entrada() != null && !r.getFecha_entrada().isEmpty()) {
                dpFechaEntrada.setValue(LocalDate.parse(r.getFecha_entrada()));
            }
            if (r.getFecha_salida() != null && !r.getFecha_salida().isEmpty()) {
                dpFechaSalida.setValue(LocalDate.parse(r.getFecha_salida()));
            }
            cbEstado.setValue(r.getEstado());
            calcularTotal();
        }
    }

    private boolean validate() {
        if (cbCliente.getSelectionModel().getSelectedIndex() < 0 ||
                cbHabitacion.getSelectionModel().getSelectedIndex() < 0 ||
                dpFechaEntrada.getValue() == null || dpFechaSalida.getValue() == null || cbEstado.getValue() == null) {
            Alerts.viewAlerts("Datos obligatorios", "Seleccione cliente, habitación, fechas y estado.", "WARNING").showAndWait();
            return false;
        }

        if (!dpFechaSalida.getValue().isAfter(dpFechaEntrada.getValue())) {
            Alerts.viewAlerts("Fechas incorrectas", "La fecha de salida debe ser posterior a la fecha de entrada.", "WARNING").showAndWait();
            return false;
        }

        Habitacion habitacion = habitaciones.get(cbHabitacion.getSelectionModel().getSelectedIndex());
        if ("Mantenimiento".equalsIgnoreCase(habitacion.getEstado()) &&
                !"Cancelada".equalsIgnoreCase(cbEstado.getValue())) {
            Alerts.viewAlerts("Habitación en mantenimiento", "Seleccione una habitación que no esté en mantenimiento.", "WARNING").showAndWait();
            return false;
        }

        if (!"Cancelada".equalsIgnoreCase(cbEstado.getValue())) {
            int idReserva = reserva == null ? 0 : reserva.getId();
            if (!reservaDao.isDisponible(habitacion.getId(), dpFechaEntrada.getValue().toString(),
                    dpFechaSalida.getValue().toString(), idReserva)) {
                Alerts.viewAlerts("Habitación reservada", "La habitación ya tiene una reserva en ese rango de fechas.", "WARNING").showAndWait();
                return false;
            }
        }
        return true;
    }

    private double calcularTotal() {
        if (lblTotal == null || cbHabitacion.getSelectionModel().getSelectedIndex() < 0 ||
                dpFechaEntrada.getValue() == null || dpFechaSalida.getValue() == null ||
                !dpFechaSalida.getValue().isAfter(dpFechaEntrada.getValue())) {
            if (lblTotal != null) {
                lblTotal.setText("S/ 0.00");
            }
            return 0;
        }
        Habitacion habitacion = habitaciones.get(cbHabitacion.getSelectionModel().getSelectedIndex());
        long noches = ChronoUnit.DAYS.between(dpFechaEntrada.getValue(), dpFechaSalida.getValue());
        double total = noches * habitacion.getPrecio();
        lblTotal.setText(String.format("S/ %.2f (%d noches)", total, noches));
        return total;
    }

    @FXML
    private void handleSave() {
        if (!validate()) {
            return;
        }
        if (reserva == null) {
            reserva = new Reserva();
        }
        Cliente cliente = clientes.get(cbCliente.getSelectionModel().getSelectedIndex());
        Habitacion habitacion = habitaciones.get(cbHabitacion.getSelectionModel().getSelectedIndex());

        reserva.setCliente_id(cliente.getId());
        reserva.setHabitacion_id(habitacion.getId());
        reserva.setCliente(cliente);
        reserva.setHabitacion(habitacion);
        reserva.setFecha_entrada(dpFechaEntrada.getValue().toString());
        reserva.setFecha_salida(dpFechaSalida.getValue().toString());
        reserva.setEstado(cbEstado.getValue());
        reserva.setTotal(calcularTotal());
        saved = true;
        cerrar();
    }

    @FXML
    private void handleCancel() {
        cerrar();
    }

    private void cerrar() {
        Stage stage = (Stage) cbCliente.getScene().getWindow();
        stage.close();
    }
}

