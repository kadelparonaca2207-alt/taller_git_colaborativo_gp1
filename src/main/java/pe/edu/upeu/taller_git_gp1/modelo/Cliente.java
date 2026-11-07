package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Cliente {
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
    }


}
