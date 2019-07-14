package com.example.videoclub.Modelos;

public class Alquiler {
    private Integer id;
    private String FechaDesde;
    private String FechaHasta;
    private String FechaEntrega;
    private double costo;
    private Pelicula pelicula;

    public Alquiler() {
        pelicula=new Pelicula();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFechaDesde() {
        return FechaDesde;
    }

    public void setFechaDesde(String fechaDesde) {
        FechaDesde = fechaDesde;
    }

    public String getFechaHasta() {
        return FechaHasta;
    }

    public void setFechaHasta(String fechaHasta) {
        FechaHasta = fechaHasta;
    }

    public double getCosto() {
        return costo;
    }

    public void setCosto(double costo) {
        this.costo = costo;
    }

    public Pelicula getPelicula() {
        return pelicula;
    }

    public void setPelicula(Pelicula pelicula) {
        this.pelicula = pelicula;
    }

    public String getFechaEntrega() {
        return FechaEntrega;
    }

    public void setFechaEntrega(String fechaEntrega) {
        FechaEntrega = fechaEntrega;
    }
}
