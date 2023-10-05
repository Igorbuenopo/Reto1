package Entidades;

import androidx.annotation.NonNull;

import java.util.Locale;

public class Fecha {
    int hora, minuto, dia, mes, ano;

    public void setAno(int ano) {
        this.ano = ano;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setHora(int hora) {
        this.hora = hora;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setMinuto(int minuto) {
        this.minuto = minuto;
    }

    public int getAno() {
        return ano;
    }

    public int getDia() {
        return dia;
    }

    public int getHora() {
        return hora;
    }

    public int getMes() {
        return mes;
    }

    public int getMinuto() {
        return minuto;
    }

    public Fecha(){

    }

    public Fecha(int dia,int mes, int ano, int hora, int minuto){
        this.dia=dia;
        this.mes=mes;
        this.ano=ano;
        this.hora=hora;
        this.minuto=minuto;
    }

    @NonNull
    @Override
    public String toString() {
        String formatoH = String.format(Locale.getDefault(),"%02d:%02d",hora,minuto);
        return dia+"-"+mes+"-"+ano+"-"+formatoH;
    }
}
