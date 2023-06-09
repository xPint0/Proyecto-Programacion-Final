/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.dao;

import com.gf.proyecto.modelo.Obras;
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
public class ObrasDAO {

    /*
    Este método inserta una obra de la base de datos 
     */
    public static int insertarObra(Obras obra) throws Exception {
        String sql = "INSERT INTO obras VALUES (nombre_obra,descripción_obra,disciplina,imagen,id_museo,id_autor) (?,?,?,?,?,?)";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obra.getNombreObra());
            ps.setString(2, obra.getDescripcionObra());
            ps.setString(3, obra.getDisciplina());
            ps.setString(4, obra.getImagen());
            ps.setInt(5, obra.getIdMuseo());
            ps.setInt(6, obra.getIdAutor());
            return ps.executeUpdate();
        }
    }

    /*
    Este método modifica una obra de la base de datos 
     */
    public static int modificarObra(Obras obra, int idObra) throws Exception {
        String sql = "UPDATE obras SET nombre_obra=?, descripción_obra=?, disciplina=?, imagen=?  WHERE id_obra=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, obra.getNombreObra());
            ps.setString(2, obra.getDescripcionObra());
            ps.setString(3, obra.getDisciplina());
            ps.setString(4, obra.getImagen());
            ps.setInt(5, idObra);
            return ps.executeUpdate();
        }
    }

    /*
    Este método borra una obra de la base de datos 
     */
    public static int borrarObra(int idObra) throws Exception {
        String sql = "DELETE  FROM  obras  WHERE id_obra= ?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idObra);
            return ps.executeUpdate();
        }
    }

    /*
    Este método devuelve verdadero o falso si la obra existe en  la base de datos 
     */
    public static boolean existeObraID(int idObra) throws Exception {
        String sql = "SELECT * FROM obras WHERE id=?";
        boolean exist = false;
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idObra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        return exist;
    }

    /*
    Este método muestra los datos de la Obra
     */
    public static void seleccionarObraPorID(int idObra) throws Exception {
        String sql = "SELECT * FROM obras WHERE id_obra=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idObra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Nombre: " + rs.getString(2)
                        + "Decripción: " + rs.getString(3) + "Disciplina: " + rs.getString(4));
            }
        }
    }

    /*
    Este método devuelve el objeto Obras 
     */
    public static Obras devolverObras(int idObra) throws Exception {
        Obras DevolverObra = null;
        String sql = "SELECT * FROM obras WHERE id_obra=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idObra);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DevolverObra = new Obras(rs.getInt(1), rs.getString(2),
                        rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getInt(6), rs.getInt(7));
            }
        }
        return DevolverObra;
    }

    /*
    Este método devuelve una lista con todas los obras de la base de datos
     */
    public static List<Obras> listarObras() throws Exception {
        String sql = "SELECT * FROM obras";
        List<Obras> listarObras = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Obras obtenerObras = new Obras();
                obtenerObras.setIdObra(rs.getInt(1));
                obtenerObras.setNombreObra(rs.getString(2));
                obtenerObras.setDescripcionObra(rs.getString(3));
                obtenerObras.setDisciplina(rs.getString(4));
                obtenerObras.setImagen(rs.getString(5));
                obtenerObras.setIdMuseo(rs.getInt(6));
                obtenerObras.setIdAutor(rs.getInt(7));
                listarObras.add(obtenerObras);
            }
        }
        return listarObras;
    }
}
