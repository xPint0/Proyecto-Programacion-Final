/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.controlador;

import com.gf.proyecto.dao.RankingDAO;
import com.gf.proyecto.modelo.Ranking;
import com.gf.proyecto.utilidades.ConexionBD;
import com.gf.proyecto.vistas.VistaRankings;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Lucia
 */
public class ControladorVistaRanking implements ActionListener{

    private final VistaRankings vistaRanking;
    private DefaultTableModel modeloRank; //Modelo por defecto de la tabla del ranking
    private static List columRank; //Lista que almacena los nombres de las columnas del ranking
    private static ResultSetMetaData rsmd; //Recupera los metadatos de las tablas de la BD

    public ControladorVistaRanking(VistaRankings vistaRanking) {
        this.vistaRanking = vistaRanking;
    }
    /*
    Método que inicia la vista 
    */
    public void iniciarVistaRanking() {
        columRank = new ArrayList();
        columRank = obtenerTitulosColumnas();
        vistaRanking.setSize(establecerTamanioVentana(0.5, 0.8));
        vistaRanking.setResizable(false);
        vistaRanking.setLocationRelativeTo(null);
        vistaRanking.setVisible(true);
        vistaRanking.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        iniciarComponentes();
        try {
            aniadirDatosTablaRanking();
        } catch (Exception ex) {
            Logger.getLogger(ControladorVistaRanking.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
     /*
    Método que establece el tamaño de la ventana dependiendo de la resolución
    de la pantalla
     */
    public static Dimension establecerTamanioVentana(double ancho, double alto) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension pantalla = toolkit.getScreenSize();
        int pantallaAncho = pantalla.width;
        int pantallaAlto = pantalla.height;
        int frameAncho = (int) (pantallaAncho * ancho);
        int frameAlto = (int) (pantallaAlto * alto);
        Dimension dimension = new Dimension(frameAncho, frameAlto);
        return dimension;
    }

    public void iniciarComponentes() {
        modeloRank = new DefaultTableModel(null, columRank.toArray());
        vistaRanking.getTablaRanking().setModel(modeloRank);  
        vistaRanking.getTablaRanking().setAutoCreateRowSorter(true);
        vistaRanking.getBotonBuscarJugador().addActionListener(this);
    }
    /*
    Método que recupera los datos de los jugadores de la BD
    */
    private void aniadirDatosTablaRanking() throws Exception {
        List<Ranking> listarRanking = RankingDAO.listarRanking();
        for (Ranking listarRanking1 : listarRanking) {
            String tiempo = String.format("%02d:%02d:%02d", listarRanking1.getTiempo().getHour(), listarRanking1.getTiempo().getMinute(), listarRanking1.getTiempo().getSecond());
            modeloRank.addRow(new String[]{String.valueOf(listarRanking1.getIdRanking()), listarRanking1.getJugador(), String.valueOf(listarRanking1.getPuntos()),
                tiempo});
        }
    }
    /*
    Método que obtiene los titulos de las columnas de la BD
    */
    private static List obtenerTitulosColumnas() {
        try (Connection con = ConexionBD.getConnection();) {
            Statement st = con.createStatement();
            String sql = "SELECT * FROM ranking";
            ResultSet rs = st.executeQuery(sql);
            rsmd = rs.getMetaData();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                columRank.add(rsmd.getColumnName(i));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaRankings.class.getName()).log(Level.SEVERE, null, ex);
        }
        return columRank;
    }
    /*
    Método que buscar el jugador y muestra los datos del ranking
     */
    @Override
    public void actionPerformed(ActionEvent e) {
         try (Connection con = ConexionBD.getConnection()) {
            String obtenerNombre = JOptionPane.showInputDialog(vistaRanking, "Introduce el nombre del jugador: ","Buscar Jugador",JOptionPane.INFORMATION_MESSAGE);
            String sql = "SELECT * FROM ranking WHERE nombre_jugador='" + obtenerNombre+"'";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                String datosJugador = "ID: " + String.valueOf(rs.getInt(1)) + "\n"
                        + "Nombre: " + rs.getString(2) + "\n"
                        + "Puntos: "+ String.valueOf(rs.getInt(3)) + "\n"
                        + "Tiempo: " + rs.getTime(4).toString();
                JOptionPane.showMessageDialog(vistaRanking, datosJugador, "Datos del jugador", JOptionPane.INFORMATION_MESSAGE);

            } else {
                JOptionPane.showMessageDialog(vistaRanking, "El Nombre introducido no esta asociado a ningun jugador", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VistaRankings.class.getName()).log(Level.SEVERE, null, ex);
        } 
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
