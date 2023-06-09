/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.modelo;

import java.time.LocalTime;

/**
 *
 * @author pinvalda
 */
public class Ranking {
    private Integer idRanking;
    private String jugador;
    private Integer puntos;
    private LocalTime tiempo;

    public Ranking() {
    }

    public Ranking(Integer idRanking, String jugador, Integer puntos, LocalTime tiempo) {
        this.idRanking = idRanking;
        this.jugador = jugador;
        this.puntos = puntos;
        this.tiempo = tiempo;
    }

    public Integer getIdRanking() {
        return idRanking;
    }

    public void setIdRanking(Integer idRanking) {
        this.idRanking = idRanking;
    }

    public String getJugador() {
        return jugador;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    public LocalTime getTiempo() {
        return tiempo;
    }

    public void setTiempo(LocalTime tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "ID: " + idRanking + " Nombre Jugador: " + jugador + " Puntos: " + puntos + " Tiempo: " + tiempo;
    }
    
}
