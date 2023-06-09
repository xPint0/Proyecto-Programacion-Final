/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.modelo;

/**
 *
 * @author pinvalda
 */
public class Museos {
    private Integer idMuseo;
    private String nombreMuseo;
    private String imagen;
    private Integer idPais;

    public Museos() {
    }

    public Museos(Integer idMuseo, String nombreMuseo, String imagen, Integer idPais) {
        this.idMuseo = idMuseo;
        this.nombreMuseo = nombreMuseo;
        this.imagen = imagen;
        this.idPais = idPais;
    }

    public Integer getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(Integer idMuseo) {
        this.idMuseo = idMuseo;
    }

    public String getNombreMuseo() {
        return nombreMuseo;
    }

    public void setNombreMuseo(String nombreMuseo) {
        this.nombreMuseo = nombreMuseo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getIdPais() {
        return idPais;
    }

    public void setIdPais(Integer idPais) {
        this.idPais = idPais;
    }

     @Override
    public String toString() {
        return "ID:" + idMuseo + " Nombre: " + nombreMuseo + ", idPais: " + idPais;
    }
    
}
