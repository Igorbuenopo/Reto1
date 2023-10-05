package Entidades;

import java.util.Date;

public class Evento {

    //Variables de uso
    private String titulo;
    private String descripcion;
    private String fecha; //Ponemos en formato String para poder maniobrar los datos con comodidad
    private String lugar;

    //GETTERS & SETTERS
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        fecha = fecha;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    //Constructor con elementos
    public Evento(String titulo, String descripcion, String lugar, String fecha){
        this.titulo=titulo;
        this.descripcion=descripcion;
        this.lugar=lugar;
        this.fecha=fecha;
    }
    //Creamos un constructor vacio por que FireBase lo solicita
    public Evento(){

    }
}
