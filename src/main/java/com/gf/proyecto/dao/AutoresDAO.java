/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.dao;

import com.gf.proyecto.modelo.Autores;
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
public class AutoresDAO {

    /*
    Este método inserta un autor de la base de datos 
     */
    public static int insertarAutor(Autores autor) throws Exception {
        String sql = "INSERT INTO autores (nombre_autor,id_pais) VALUES (?,?)";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, autor.getNombre_autor());
            ps.setInt(2, autor.getIdPais());
            return ps.executeUpdate();
        }
    }

    /*
    Este método modifica un autor de la base de datos 
     */
    public static int modificarAutor(Autores autor, int idAutor) throws Exception {
        String sql = "UPDATE autores SET nombre_autor=? , id_pais=?  WHERE id_autor=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, autor.getNombre_autor());
            ps.setInt(2, autor.getIdPais());
            ps.setInt(3, idAutor);
            return ps.executeUpdate();
        }
    }

    /*
    Este método borra un autor de la base de datos 
     */
    public static int borrarAutor(int idAutor) throws Exception {
        String sql = "DELETE  FROM  autores  WHERE id_autor=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idAutor);
            return ps.executeUpdate();
        }
    }

    /*
    Este método devuelve verdadero o falso si el autor existe en  la base de datos 
     */
    public static boolean existeAutorID(int idAutor) throws Exception {
        String sql = "SELECT * FROM autores WHERE id_autor=?";
        boolean exist = false;
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        }
        return exist;
    }

    /*
    Este método muestra los datos del autor
     */
    public static void seleccionarAutorPorID(int idAutor) throws Exception {
        String sql = "SELECT * FROM autores WHERE id_autor=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                System.out.println("ID: " + rs.getInt(1) + " Nombre: " + rs.getString(2));
            }
        }
    }

    /*
    Este método devuelve el objeto Autor 
     */
    public static Autores devolverAutor(int idAutor) throws Exception {
        Autores DevolverAutor = null;
        String sql = "SELECT * FROM autores WHERE id_autor=?";
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, idAutor);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                DevolverAutor = new Autores(rs.getInt(1), rs.getString(2),
                        rs.getInt(3));
            }
        }
        return DevolverAutor;
    }

    /*
    Este método devuelve una lista con todos los autores de la base de datos
     */
    public static List<Autores> listarAutores() throws Exception {
        String sql = "SELECT * FROM autores";
        List<Autores> listaAutores = new ArrayList<>();
        try (Connection conn = ConexionBD.getConnection()) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Autores obtenerAutores = new Autores();
                obtenerAutores.setIdAutor(rs.getInt(1));
                obtenerAutores.setNombre_autor(rs.getString(2));
                obtenerAutores.setIdPais(rs.getInt(3));
                listaAutores.add(obtenerAutores);
            }
        }
        return listaAutores;
    }
}
