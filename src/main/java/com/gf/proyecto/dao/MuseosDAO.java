/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.dao;

import com.gf.proyecto.modelo.Museos;
import com.gf.proyecto.utilidades.ConexionBD;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Lucia
 */
public class MuseosDAO {

    /*
    Este método inserta un museo de la base de datos 
     */
    public static int insertarMuseo(Museos museo) throws Exception {
        String sql = "INSERT INTO museos (nombre_museo,imagen,id_pais) VALUES (?,?,?)";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, museo.getNombreMuseo());
            ps.setString(2, museo.getImagen());
            ps.setInt(3, museo.getIdPais());
            return ps.executeUpdate();
        }
    }

    /*
    Este método modifica un museo de la base de datos 
     */
    public static int modificarMuseo(Museos museo, int idMuseo) throws Exception {
        String sql = "UPDATE museos SET nombre_museo=? , imagen=? , id_pais=? WHERE id_museo=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, museo.getNombreMuseo());
            ps.setString(1, museo.getImagen());
            ps.setInt(3, museo.getIdPais());
            ps.setInt(4, idMuseo);
            return ps.executeUpdate();
        }
    }

    /*
    Este método borra un museo de la base de datos 
     */
    public static int borrarMuseo(int idMuseo) throws Exception {
        String sql = "DELETE  FROM  museos  WHERE id_museo= ?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMuseo);
            return ps.executeUpdate();
        }
    }

    /*
    Este método devuelve verdadero o falso si el museo existe en  la base de datos 
     */
    public static boolean existeMuseoID(int idMuseo) throws Exception {
        String sql = "SELECT * FROM museos WHERE id_museo=?";
        boolean exist = false;
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMuseo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        return exist;
    }

    /*
    Este método muestra los datos del museo
     */
    public static void seleccionarMuseoPorID(int idMuseo) throws Exception {
        String sql = "SELECT * FROM museos WHERE id_museo=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMuseo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Nombre: " + rs.getString(2));
            }
        }
    }

    /*
    Este método devuelve el objeto Museo
     */
    public static Museos devolverMuseo(int idMuseo) throws Exception {
        Museos DevolverMuseo = null;
        String sql = "SELECT * FROM museos WHERE id_museo=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idMuseo);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DevolverMuseo = new Museos(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getInt(4));
            }
        }
        return DevolverMuseo;
    }

    /*
    Este método devuelve una lista con todos los museos de la base de datos
     */
    public static List<Museos> listarMuseos() throws Exception {
        String sql = "SELECT * FROM museos";
        List<Museos> listaMuseos = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Museos obtenerMuseos = new Museos();
                obtenerMuseos.setIdMuseo(rs.getInt(1));
                obtenerMuseos.setNombreMuseo(rs.getString(2));
                obtenerMuseos.setIdPais(rs.getInt(4));
                listaMuseos.add(obtenerMuseos);
            }
        }
        return listaMuseos;
    }
}
