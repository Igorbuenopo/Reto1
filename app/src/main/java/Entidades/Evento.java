package Entidades;

import java.io.Serializable;
import java.util.Date;

public class Evento implements Serializable {

    //Variables de uso
    private String titulo;
    private String descripcion;

    public Evento(String titulo, String descripcion, int hora, int minuto, int dia, int mes, int ano, String lugar) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.hora = hora;
        this.minuto = minuto;
        this.dia = dia;
        this.mes = mes;
        this.ano = ano;
        this.lugar = lugar;
    }

    public int getHora() {
        return hora;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public int getMinuto() {
        return minuto;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getDia() {
        return dia;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public int getMes() {
        return mes;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    //   private Fecha fecha;
 private int hora, minuto, dia, mes, ano;
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

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }
    //Constructor con elementos

    //Creamos un constructor vacio por que FireBase lo solicita
    public Evento(){

    }
}
