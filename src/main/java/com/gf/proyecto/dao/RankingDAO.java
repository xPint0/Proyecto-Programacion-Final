/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.dao;

import com.gf.proyecto.modelo.Ranking;
import com.gf.proyecto.utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucia
 */
public class RankingDAO {

    /*
    Este método inserta un Jugador de la base de datos 
     */
    public static int insertarJugador(Ranking ranking) throws Exception {
        String sql = "INSERT INTO ranking (nombre_jugador,puntos,tiempo) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, ranking.getJugador());
            ps.setInt(2, ranking.getPuntos());
            ps.setTime(3, Time.valueOf(ranking.getTiempo()));
            return ps.executeUpdate();
        }
    }

    /*
    Este método modifica un jugador de la base de datos 
     */
    public static int modificarJugador(Ranking ranking, String nombre) throws Exception {
        String sql = "UPDATE ranking SET  puntos=?, tiempo=? WHERE nombre_jugador=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, ranking.getPuntos());
            ps.setTime(2, Time.valueOf(ranking.getTiempo()));
            ps.setString(3, nombre);
            return ps.executeUpdate();
        }
    }

    /*
    Este método borra un jugador de la base de datos 
     */
    public static int borrarJugador(String nombre) throws Exception {
        String sql = "DELETE  FROM  ranking  WHERE nombre_jugador= ?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            return ps.executeUpdate();
        }
    }

    /*
    Este método devuelve verdadero o falso si el Jugador existe en  la base de datos 
     */
    public static boolean existeJugadorPorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM ranking WHERE nombre_jugador=?";
        boolean exist = false;
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        return exist;
    }

    /*
    Este método muestra los datos del jugador
     */
    public static void seleccionarJugadorPorNombre(String nombre) throws Exception {
        String sql = "SELECT * FROM ranking WHERE nombre_jugador=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Nombre: " + rs.getString(2)
                        + "Puntos: " + rs.getInt(3) + " Tiempo: " + rs.getTime(4));
            }
        }
    }

    /*
    Este método devuelve el objeto Jugador 
     */
    public static Ranking devolverJugador(String nombre) throws Exception {
        Ranking DevolverJugador = null;
        String sql = "SELECT * FROM ranking WHERE nombre_jugador=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, nombre);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DevolverJugador = new Ranking(rs.getInt(1), rs.getString(2),
                        rs.getInt(3), rs.getTime(4).toLocalTime());
            }
        }
        return DevolverJugador;
    }

    /*
    Este método devuelve una lista con todos los jugadores de la base de datos
     */
    public static List<Ranking> listarRanking() throws Exception {
        String sql = "SELECT * FROM ranking";
        List<Ranking> listarJugadores = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Ranking obtenerRanking = new Ranking();
                obtenerRanking.setIdRanking(rs.getInt(1));
                obtenerRanking.setJugador(rs.getString(2));
                obtenerRanking.setPuntos(rs.getInt(3));
                obtenerRanking.setTiempo(rs.getTime(4).toLocalTime());
                listarJugadores.add(obtenerRanking);
            }
        }
        return listarJugadores;
    }
}
