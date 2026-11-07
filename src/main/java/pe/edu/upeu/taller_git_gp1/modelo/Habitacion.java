package pe.edu.upeu.taller_git_gp1.modelo;

import javafx.beans.property.*;

public class Habitacion {
    private final IntegerProperty id = new SimpleIntegerProperty();
    private final IntegerProperty numero_hab = new SimpleIntegerProperty();
    private final IntegerProperty piso = new SimpleIntegerProperty();
    private final StringProperty tipo = new SimpleStringProperty();
    private final DoubleProperty precio = new SimpleDoubleProperty();
    private final StringProperty estado = new SimpleStringProperty();

    public Habitacion() {
    }

    public final String getEstado() {
        return estado.get();
    }

    public final void setEstado(String value) {
        estado.set(value);
    }

    public final StringProperty estadoProperty() {
        return estado;
    }


    public final double getPrecio() {
        return precio.get();
    }

    public final void setPrecio(double value) {
        precio.set(value);
    }

    public final DoubleProperty precioProperty() {
        return precio;
    }


    public final String getTipo() {
        return tipo.get();
    }

    public final void setTipo(String value) {
        tipo.set(value);
    }

    public final StringProperty tipoProperty() {
        return tipo;
    }


    public final int getPiso() {
        return piso.get();
    }

    public final void setPiso(int value) {
        piso.set(value);
    }

    public final IntegerProperty pisoProperty() {
        return piso;
    }


    public final int getNumero_hab() {
        return numero_hab.get();
    }

    public final void setNumero_hab(int value) {
        numero_hab.set(value);
    }

    public final IntegerProperty numero_habProperty() {
        return numero_hab;
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

}
