package Entidades;


//Clase para las funciones de la calculadora
public class Calculadora {

    public int valor1;
    public int valor2;

    public Calculadora(){

    }

    //Funcion para sumar
    public int Suma(int valor1,int valor2){
        return valor1+valor2;
    }

    //Funcion para restar
    public int Resta(int valor1,int valor2){
        return valor1-valor2;
    }

    //Funcion para multiplicar
    public int Mult(int valor1,int valor2){
        return valor1*valor2;
    }

    //Funcion para dividir
    public int Div(int valor1,int valor2){
        return valor1/valor2;
    }
}
