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
import pe.edu.upeu.taller_git_gp1.implementacion.ClienteDaoImpl;
import pe.edu.upeu.taller_git_gp1.interfaces.IClienteDao;
import pe.edu.upeu.taller_git_gp1.modelo.Cliente;
import pe.edu.upeu.taller_git_gp1.utils.Alerts;

import java.util.Optional;

public class ClienteController {
    
    @FXML
    private TableView<Cliente> tableCliente;

    @FXML
    private TableColumn<Cliente, Number> colId;

    @FXML
    private TableColumn<Cliente, String> colNombre;

    @FXML
    private TableColumn<Cliente, String> colApellido;

    @FXML
    private TableColumn<Cliente, Number> colDocumento;

    @FXML
    private TableColumn<Cliente, String> colCorreo;

    @FXML
    private TableColumn<Cliente, String> colTelefono;

    @FXML
    private TextField txtBuscar;

    private final IClienteDao clienteDao = new ClienteDaoImpl();

    private ObservableList<Cliente> listaClientes;


    @FXML
    public void initialize() {

        colId.setCellValueFactory(
                cellData -> cellData.getValue().idProperty());

        colNombre.setCellValueFactory(
                cellData -> cellData.getValue().nombreProperty());

        colApellido.setCellValueFactory(
                cellData -> cellData.getValue().apellidoProperty());

        colDocumento.setCellValueFactory(
                cellData -> cellData.getValue().numero_documentoProperty());

        colCorreo.setCellValueFactory(
                cellData -> cellData.getValue().correoProperty());

        colTelefono.setCellValueFactory(
                cellData -> cellData.getValue().telefonoProperty());

        cargarDatos();
    }


    private void cargarDatos() {

        listaClientes = FXCollections.observableArrayList(clienteDao.listAll());

        tableCliente.setItems(listaClientes);
    }


    @FXML
    private void handleDelete() {

        Cliente selectClient = tableCliente.getSelectionModel().getSelectedItem();

        if (selectClient != null) {

            Alert confirm = Alerts.viewAlerts(
                    "Confirmar eliminación",
                    "¿Está seguro de eliminar al cliente: " +
                            selectClient.getNombre() + " " +
                            selectClient.getApellido() + "?",
                    "CONFIRMATION");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {

                if (clienteDao.delete(selectClient)) {

                    Alerts.viewAlerts(
                            "Correcto",
                            "Cliente eliminado correctamente.",
                            "INFORMATION").showAndWait();

                    listaClientes.remove(selectClient);

                } else {

                    Alerts.viewAlerts(
                            "No se pudo eliminar",
                            "El cliente puede tener reservas asociadas.",
                            "WARNING").showAndWait();
                }
            }

        } else {

            Alerts.viewAlerts(
                    "Selección requerida",
                    "Seleccione un cliente para eliminar.",
                    "WARNING").showAndWait();
        }
    }


    @FXML
    private void handleNew() {

        showForm(null);
    }


    @FXML
    private void handleEdit() {

        Cliente selectClient = tableCliente.getSelectionModel().getSelectedItem();

        if (selectClient != null) {

            Cliente clienteCompleto = clienteDao.searchById(selectClient.getId());

            showForm(clienteCompleto);

        } else {

            Alerts.viewAlerts(
                    "Selección requerida",
                    "Seleccione un cliente para editar.",
                    "WARNING").showAndWait();
        }
    }


    @FXML
    private void handleSearch() {

        String documento = txtBuscar.getText().trim();

        if (documento.isEmpty()) {

            cargarDatos();

        } else {

            Cliente cliente = clienteDao.searchByDni(documento);

            listaClientes = FXCollections.observableArrayList();

            if (cliente != null) {
                listaClientes.add(cliente);
            }

            tableCliente.setItems(listaClientes);
        }
    }


    @FXML
    private void handleClear() {

        txtBuscar.clear();

        cargarDatos();
    }


    private void showForm(Cliente c) {

        try {

            FXMLLoader loader = new FXMLLoader(HelloApplication.class
                    .getResource("/pe/edu/upeu/taller_git_gp1/views/cliente-form.fxml"));

            Parent root = loader.load();

            ClienteFormController controller = loader.getController();

            controller.setCliente(c); //Pasamos null si es nuevo

            Stage stage = new Stage();

            stage.setTitle(c == null ? "Nuevo Cliente" : "Editar Cliente");

            stage.initModality(Modality.APPLICATION_MODAL);

            stage.initOwner(tableCliente.getScene().getWindow());

            stage.setScene(new Scene(root));

            stage.showAndWait();

            if (controller.isSaved()) {

                Cliente clienteProcesado = controller.getCliente();

                boolean resultado;

                if (c == null) {

                    resultado = clienteDao.insert(clienteProcesado);

                    if (resultado) {

                        Alerts.viewAlerts(
                                "Correcto",
                                "Cliente registrado correctamente.",
                                "INFORMATION").showAndWait();

                    } else {

                        Alerts.viewAlerts(
                                "Error",
                                "No se pudo registrar el cliente. Verifique el número de documento.",
                                "ERROR").showAndWait();
                    }

                } else {

                    resultado = clienteDao.update(clienteProcesado);

                    if (resultado) {

                        Alerts.viewAlerts(
                                "Correcto",
                                "Cliente actualizado correctamente.",
                                "INFORMATION").showAndWait();

                    } else {

                        Alerts.viewAlerts(
                                "Error",
                                "No se pudo actualizar el cliente.",
                                "ERROR").showAndWait();
                    }
                }

                cargarDatos();
            }

        } catch (Exception e) {

            Alerts.viewAlerts(
                    "Error",
                    "No se pudo abrir el formulario: " + e.getMessage(),
                    "ERROR").showAndWait();
        }
    }

}
