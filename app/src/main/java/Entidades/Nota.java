package Entidades;

public class Nota {

    private String titulo;
    private String contenido;
    private String time;
    private String docId;

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Nota(String titulo, String contenido, String time) {
        this.titulo = titulo;
        this.contenido = contenido;
        this.time = time;
    }

    public Nota() {
    }

    public String getTitulo() {
        return titulo;
    }

    public String getContenido() {
        return contenido;
    }


    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

}
