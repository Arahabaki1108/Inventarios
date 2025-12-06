package com.arahabaki.inventarios;

public class Movimiento {
    private int id_finanza;
    private String tipo;
    private double monto;
    private String fecha;
    private String descripcion;

    public Movimiento(int id, String tipo, double monto, String fecha, String descripcion) {
        this.id_finanza = id;
        this.tipo = tipo;
        this.monto = monto;
        this.fecha = fecha;
        this.descripcion = descripcion;
    }

    public int getId() { return id_finanza; }
    public String getTipo() { return tipo; }
    public double getMonto() { return monto; }
    public String getFecha() { return fecha; }
    public String getDescripcion() { return descripcion; }
}