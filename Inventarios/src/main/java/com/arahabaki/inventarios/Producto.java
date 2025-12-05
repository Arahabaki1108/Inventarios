package com.arahabaki.inventarios;

public class Producto {
    private int id_Producto;
    private String nombre;
    private String categoria;
    private double precio_Venta;
    private double precio_Compra;
    private int stock;

    public Producto(int id_Producto, String nombre, String categoria, double precio_Venta, double precio_Compra, int stock){
        this.id_Producto = id_Producto;
        this.nombre = nombre;
        this.categoria = categoria;
        this.precio_Venta = precio_Venta;
        this.precio_Compra = precio_Compra;
        this.stock = stock;
    }
    public int getId() {return id_Producto;}
    public String getNombre() {return nombre;}
    public String getCategoria() {return categoria;}
    public double getPrecio() {return precio_Venta;}
    public double getCosto() {return precio_Compra;}
    public int getStock() {return stock;}
}
