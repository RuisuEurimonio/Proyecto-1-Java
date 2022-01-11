/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.demo.modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author Usuario
 */
@Table("productos")
public class Producto {
    
    @Id
    @Column("codigo")
    private Long id;
    @Column("nombre")
    private String nombre;
    @Column("precio")
    private Double precio;
    @Column("inventario")
    private Integer inventario;

    public Producto() {
    }

    public Producto(Long id, String nombre, Double precio, Integer inventario) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }
    

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Double getPrecio() {
        return precio;
    }

    public Integer getInventario() {
        return inventario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public void setInventario(Integer inventario) {
        this.inventario = inventario;
    }
    
    
}
