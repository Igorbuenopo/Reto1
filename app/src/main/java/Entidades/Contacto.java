package Entidades;

import android.net.Uri;

import java.util.Locale;

public class Contacto {
    String nombre;
    int numero;
    String direccion;


    public Contacto(String nombre, int numero, String direccion, String descripcion) {
        this.nombre = nombre;
        this.numero = numero;
        this.direccion = direccion;

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

    public String toString() {
        return "Contacto - Nombre: " +nombre +" - Numero: " +numero +" - Direccion: " +direccion;
    }
}
