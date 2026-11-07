package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
<<<<<<< HEAD
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final StringProperty nombre = new SimpleStringProperty();
    private final StringProperty apellido = new SimpleStringProperty();
    private final IntegerProperty numero_documento = new SimpleIntegerProperty();
    private final StringProperty correo = new SimpleStringProperty();
    private final StringProperty telefono = new SimpleStringProperty();

    public Cliente() {
    }

    public final int getId() {
        return id.get();
    }

    public final void setId(int value) {
        id.set(value);
    }

    public final IntegerProperty idProperty() {
        return id;
    }


    public final String getTelefono() {
        return telefono.get();
    }

    public final void setTelefono(String value) {
        telefono.set(value);
    }

    public final StringProperty telefonoProperty() {
        return telefono;
    }


    public final String getCorreo() {
        return correo.get();
    }

    public final void setCorreo(String value) {
        correo.set(value);
    }

    public final StringProperty correoProperty() {
        return correo;
    }


    public final int getNumero_documento() {
        return numero_documento.get();
    }

    public final void setNumero_documento(int value) {
        numero_documento.set(value);
    }

    public final IntegerProperty numero_documentoProperty() {
        return numero_documento;
    }

    public final String getApellido() {
        return apellido.get();
    }

    public final void setApellido(String value) {
        apellido.set(value);
    }

    public final StringProperty apellidoProperty() {
        return apellido;
    }


    public final String getNombre() {
        return nombre.get();
    }

    public final void setNombre(String value) {
        nombre.set(value);
    }

    public final StringProperty nombreProperty() {
        return nombre;
=======
    private final IntegerProperty idcliente = new SimpleIntegerProperty();
    private final StringProperty Nombres = new SimpleStringProperty();
    private final StringProperty Apellidos = new SimpleStringProperty();
    private final StringProperty Tipo_Documento = new SimpleStringProperty();
    private final IntegerProperty Numero_Documento = new SimpleIntegerProperty();
    private final StringProperty Correo = new SimpleStringProperty();
    private final IntegerProperty Telefono = new SimpleIntegerProperty();

    public final int getTelefono() {
        return Telefono.get();
    }

    public final void setTelefono(int value) {
        Telefono.set(value);
    }

    public final IntegerProperty TelefonoProperty() {
        return Telefono;
    }

    public final String getCorreo() {
        return Correo.get();
    }

    public final void setCorreo(String value) {
        Correo.set(value);
    }

    public final StringProperty CorreoProperty() {
        return Correo;
    }

    public final int getNumero_Documento() {
        return Numero_Documento.get();
    }

    public final void setNumero_Documento(int value) {
        Numero_Documento.set(value);
    }

    public final IntegerProperty Numero_DocumentoProperty() {
        return Numero_Documento;
    }

    public final String getTipo_Documento() {
        return Tipo_Documento.get();
    }

    public final void setTipo_Documento(String value) {
        Tipo_Documento.set(value);
    }

    public final StringProperty Tipo_DocumentoProperty() {
        return Tipo_Documento;
    }

    public final String getApellidos() {
        return Apellidos.get();
    }

    public final void setApellidos(String value) {
        Apellidos.set(value);
    }

    public final StringProperty ApellidosProperty() {
        return Apellidos;
    }

    public final String getNombres() {
        return Nombres.get();
    }

    public final void setNombres(String value) {
        Nombres.set(value);
    }

    public final StringProperty NombresProperty() {
        return Nombres;
    }

    public final int getIdcliente() {
        return idcliente.get();
    }

    public final void setIdcliente(int value) {
        idcliente.set(value);
    }

    public final IntegerProperty idclienteProperty() {
        return idcliente;
>>>>>>> dae47a61386747187bd8b06d3783ed4c44bac4ff
    }


}
