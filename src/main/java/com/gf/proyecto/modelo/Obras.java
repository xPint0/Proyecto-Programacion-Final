/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.modelo;

/**
 *
 * @author pinvalda
 */
public class Obras {

    private Integer idObra;
    private String nombreObra;
    private String descripcionObra;
    private String disciplina;
    private String imagen;
    private Integer idMuseo;
    private Integer idAutor;

    public Obras() {
    }

    public Obras(Integer idObra, String nombreObra, String descripcionObra, String disciplina, String imagen, Integer idMuseo, Integer idAutor) {
        this.idObra = idObra;
        this.nombreObra = nombreObra;
        this.descripcionObra = descripcionObra;
        this.disciplina = disciplina;
        this.imagen = imagen;
        this.idMuseo = idMuseo;
        this.idAutor = idAutor;
    }

    public Integer getIdObra() {
        return idObra;
    }

    public void setIdObra(Integer idObra) {
        this.idObra = idObra;
    }

    public String getNombreObra() {
        return nombreObra;
    }

    public void setNombreObra(String nombreObra) {
        this.nombreObra = nombreObra;
    }

    public String getDescripcionObra() {
        return descripcionObra;
    }

    public void setDescripcionObra(String descripcionObra) {
        this.descripcionObra = descripcionObra;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Integer getIdMuseo() {
        return idMuseo;
    }

    public void setIdMuseo(Integer idMuseo) {
        this.idMuseo = idMuseo;
    }

    public Integer getIdAutor() {
        return idAutor;
    }

    public void setIdAutor(Integer idAutor) {
        this.idAutor = idAutor;
    }

    @Override
    public String toString() {
        return "ID: " + idObra + " Nombre: " + nombreObra + " Descripción: " + descripcionObra + " Disciplina: " + disciplina;
    }

    

}
