/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.dao;

import com.gf.proyecto.modelo.Paises;
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
public class PaisesDAO {

    /*
    Este método inserta un País de la base de datos 
     */
    public static int insertarPais(Paises pais) throws Exception {
        String sql = "INSERT INTO paises VALUES (nombre_pais) (?)";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pais.getNombrePais());
            return ps.executeUpdate();
        }
    }

    /*
    Este método modifica un Pais de la base de datos 
     */
    public static int modificarPais(Paises pais, int idPais) throws Exception {
        String sql = "UPDATE paises SET nombre_pais=?  WHERE id=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, pais.getNombrePais());
            ps.setInt(2, idPais);
            return ps.executeUpdate();
        }
    }

    /*
    Este método borra un Pais de la base de datos 
     */
    public static int borrarPais(int idPais) throws Exception {
        String sql = "DELETE  FROM  paises  WHERE id= ?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPais);
            return ps.executeUpdate();
        }
    }

    /*
    Este método devuelve verdadero o falso si el Pais existe en  la base de datos 
     */
    public static boolean existePaisID(int idPais) throws Exception {
        String sql = "SELECT * FROM paises WHERE id=?";
        boolean exist = false;
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPais);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        return exist;
    }

    /*
    Este método muestra los datos del Pais
     */
    public static void seleccionarPaisPorID(int idPais) throws Exception {
        String sql = "SELECT * FROM paises WHERE id_pais=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPais);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Nombre: " + rs.getString(2)
                        + "Decripción: " + rs.getString(3) + "Disciplina: " + rs.getString(4));
            }
        }
    }

    /*
    Este método devuelve el objeto Pais 
     */
    public static Paises devolverPais(int idPais) throws Exception {
        Paises DevolverPais = null;
        String sql = "SELECT * FROM paises WHERE id_pais=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idPais);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DevolverPais = new Paises(rs.getInt(1), rs.getString(2));
            }
        }
        return DevolverPais;
    }

    /*
    Este método devuelve una lista con todos los paises de la base de datos
     */
    public static List<Paises> listarPaises() throws Exception {
        String sql = "SELECT * FROM paises";
        List<Paises> listarPaises = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Paises obtenerPaises = new Paises();
                obtenerPaises.setIdPais(rs.getInt(1));
                obtenerPaises.setNombrePais(rs.getString(2));
                listarPaises.add(obtenerPaises);
            }
        }
        return listarPaises;
    }
}
