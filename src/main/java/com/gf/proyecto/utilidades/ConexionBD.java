/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.utilidades;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Lucia
 */
public class ConexionBD {

    private static Connection conn;
    private static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/dim_gf";
    private static final String MYSQL_USER = "root";
    private static final String MYSQL_PASSWD = "";
    /*
    Creación de la conexión con la base de datos con el API JDBC de forma nativa
    */
    public static Connection getConnection() throws SQLException {
        conn = DriverManager.getConnection(MYSQL_DB_URL, MYSQL_USER, MYSQL_PASSWD);
        return conn;

    }
}
