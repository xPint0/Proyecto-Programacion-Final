/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.modelo;

/**
 *
 * @author pinvalda
 */
public class Autores {
    private Integer idAutor;
    private String nombreAutor;
    private Integer idPais;

    public Autores() {
    }

    public Autores(Integer idAutor, String nombreAutor, Integer idPais) {
        this.idAutor = idAutor;
        this.nombreAutor = nombreAutor;
        this.idPais = idPais;
    }

    public int getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(int idAutor) {
        this.idAutor = idAutor;
    }

    public String getNombre_autor() {
        return nombreAutor;
    }

    public void setNombre_autor(String nombre_autor) {
        this.nombreAutor = nombre_autor;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    @Override
    public String toString() {
        return "ID:" + idAutor + " Nombre:" + nombreAutor + " idPais: " + idPais;
    }
    
}
