package Entidades;

import java.util.Locale;

public class Contacto {
    String nombre;

    int numero;
    String direccion;
    String descripcion;

    public Contacto(String nombre, int numero, String direccion, String descripcion) {
        this.nombre = nombre;
        this.numero = numero;
        this.direccion = direccion;
        this.descripcion = descripcion;
    }

    public Contacto() {

    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String toString() {
        return "Contacto - Nombre: " +nombre +" - Numero: " +numero +" - Direccion: " +direccion +" -Descripcion: " +descripcion;
    }
}
