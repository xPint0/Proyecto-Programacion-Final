/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.controlador;

import com.gf.proyecto.utilidades.ConexionBD;
import com.gf.proyecto.vistas.SwingWaypoint;
import com.gf.proyecto.vistas.SwingWaypointOverlayPainter;
import com.gf.proyecto.vistas.VistaJuego4;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.event.MouseInputListener;
import org.jxmapviewer.JXMapViewer;
import org.jxmapviewer.OSMTileFactoryInfo;
import org.jxmapviewer.cache.FileBasedLocalCache;
import org.jxmapviewer.input.CenterMapListener;
import org.jxmapviewer.input.PanKeyListener;
import org.jxmapviewer.input.PanMouseInputListener;
import org.jxmapviewer.input.ZoomMouseWheelListenerCenter;
import org.jxmapviewer.viewer.DefaultTileFactory;
import org.jxmapviewer.viewer.GeoPosition;
import org.jxmapviewer.viewer.TileFactoryInfo;
import org.jxmapviewer.viewer.WaypointPainter;

/**
 *
 * @author Lucia
 */
public class ControladorVistaJuego4 implements MouseListener {

    private final VistaJuego4 vistajuego4;
    private long tiempoInicio;  //Variable que almacena el momento inicial de arranque del juego
    private String tiempoJuego4;    //Variable convierte a String el tiempo total del juego
    private int respuestasCorrectasUsuario; //Variable que almacena las respuestas correctas del usuario
    private List imagenes;   //Lista que almacena la url de las imágenes del juego
    private List respuestasPaises;  //Variable que almacena las respuestas correctas del usuario
    private List paises;    //Lista que almacena el nombre de los paises
    private JLabel[] etiquetas; //Array de Jlabel que almacena las etiquetas del juego
    private int contadorPaises;  //Contador que indica la posicion de la imagen actual
    //Variables que almacena los valores de  etiquetas de las imagenes
    private static JLabel etiquetaImagenJuego1;
    private static JLabel etiquetaImagenJuego2;
    private static JLabel etiquetaImagenJuego3;
    private static JLabel etiquetaImagenJuego4;
    private static JLabel etiquetaImagenJuego5;
    private static JLabel etiquetaImagenJuego6;

    public ControladorVistaJuego4(VistaJuego4 vistajuego4) {
        this.vistajuego4 = vistajuego4;
    }

    /*
    Método que carga la vista inicial del juego, junto con los datos de las imágenes 
    y los paises e inicia el timer 
     */
    public void iniciarVistaJuego4() {
        imagenes = new ArrayList();
        respuestasPaises = new ArrayList();
        paises = new ArrayList();
        try {
            recuperarDatos();
            recuperarPaises();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVistaJuego4.class.getName()).log(Level.SEVERE, null, ex);
        }
        establecerEtiquetasImagenes();
        cargarImagenes();
        vistajuego4.getContentPane().add(vistajuego4.getjPanel6().add(mapa()));
        vistajuego4.setResizable(false);
        vistajuego4.setExtendedState(JFrame.MAXIMIZED_BOTH);
        vistajuego4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistajuego4.setVisible(true);
        JOptionPane.showMessageDialog(vistajuego4, "Los iconos muestran los paises disponibles\nPulsa en la imagen para seleccionar el país", "Mapa de referencia", JOptionPane.INFORMATION_MESSAGE);
        inicioTimer();
        etiquetaImagenJuego1.addMouseListener(this);
        etiquetaImagenJuego2.addMouseListener(this);
        etiquetaImagenJuego3.addMouseListener(this);
        etiquetaImagenJuego4.addMouseListener(this);
        etiquetaImagenJuego5.addMouseListener(this);
        etiquetaImagenJuego6.addMouseListener(this);

    }

    /*
    Método que establece las etiquetas de las imágenes en el Array
     */
    public void establecerEtiquetasImagenes() {
        etiquetaImagenJuego1 = vistajuego4.getEtiquetaImagen1();
        etiquetaImagenJuego2 = vistajuego4.getEtiquetaImagen2();
        etiquetaImagenJuego3 = vistajuego4.getEtiquetaImagen3();
        etiquetaImagenJuego4 = vistajuego4.getEtiquetaImagen4();
        etiquetaImagenJuego5 = vistajuego4.getEtiquetaImagen5();
        etiquetaImagenJuego6 = vistajuego4.getEtiquetaImagen6();
        etiquetas = new JLabel[]{etiquetaImagenJuego1, etiquetaImagenJuego2, etiquetaImagenJuego3, etiquetaImagenJuego4, etiquetaImagenJuego5, etiquetaImagenJuego6};
    }

    /*
    Método que carga las imágenes y las establece de la etiqueta correspondiente
     */
    private void cargarImagenes() {
        for (int i = 0; i < imagenes.size(); i++) {
            ImageIcon imagen = new ImageIcon(imagenes.get(i).toString());
            Image reescalar = imagen.getImage().getScaledInstance(220, 170, Image.SCALE_SMOOTH);
            etiquetas[i].setIcon(new ImageIcon(reescalar));
        }

    }

    /*
    Método que recupera los datos las imagenes y el las respuestas de los países de la BD 
     */
    public void recuperarDatos() throws SQLException {
        Connection con = ConexionBD.getConnection();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * from obras, paises, museos where id_obra in(6,7,8,9,10,11) and obras.id_obra = museos.id_museo and museos.id_pais = paises.id_pais")) {
            while (rs.next()) {
                imagenes.add(rs.getString("imagen"));
                respuestasPaises.add(rs.getString("nombre_pais"));
            }
        }
    }

    /*
    Método que recupera el nombre de los paises
     */
    public void recuperarPaises() throws SQLException {

        Connection con = ConexionBD.getConnection();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * from paises")) {
            while (rs.next()) {
                paises.add(rs.getString(2));

            }
        }

    }

    /* 
    Método que almacena en la variable el tiempo inicial
     */
    private void inicioTimer() {
        tiempoInicio = System.nanoTime();
    }

    /*
    Método que finaliza el timer y muestra las respuestas correctas del usuario
     */
    private void finTimer() {
        JOptionPane.showMessageDialog(vistajuego4, "Has acertado " + respuestasCorrectasUsuario + " de 6");
        long tiempoFin = System.nanoTime();
        long tiempoJuego4segundos = (tiempoFin - tiempoInicio) / 1000000000;
        long horas = tiempoJuego4segundos / 3600;
        long minutos = (tiempoJuego4segundos % 3600) / 60;
        long segundos = (tiempoJuego4segundos % 3600) % 60;
        tiempoJuego4 = String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    /*
    Método que formatea el tiempo final del juego y devuelve un LocalTime
     */
    public LocalTime tiempoFinal() {
        return LocalTime.parse(tiempoJuego4, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /*
    Método que comprueba el contador finaliza el juego cuando el contador de paises
    llegue al tamaño de la lista
     */
    public void comprobarContador() {
        if (contadorPaises == respuestasPaises.size()) {
            finTimer();
            try {
                ControladorVistaPrincipal.recuperarResultadosJuego4(tiempoFinal(), respuestasCorrectasUsuario);
            } catch (Exception ex) {
                Logger.getLogger(ControladorVistaJuego4.class.getName()).log(Level.SEVERE, null, ex);
            }
            this.vistajuego4.dispose();
        }
    }

    /*
    Método que establece las posiciones de los países en el mapa y llama a la
    clase que crea los botones y los coloca en el mapa
     */
    private JXMapViewer mapa() {
        // Create a TileFactoryInfo for OSM
        TileFactoryInfo info = new OSMTileFactoryInfo();
        DefaultTileFactory tileFactory = new DefaultTileFactory(info);
        tileFactory.setThreadPoolSize(8);

        // Setup local file cache
        File cacheDir = new File(System.getProperty("user") + File.separator + ".jxmapviewer2");
        tileFactory.setLocalCache(new FileBasedLocalCache(cacheDir, false));
        // Setup JXMapViewer
        JXMapViewer mapViewer = new JXMapViewer();
        mapViewer.setTileFactory(tileFactory);

        GeoPosition centromapa = new GeoPosition(50.35699, 13.79667);
        GeoPosition España = new GeoPosition(40.4167278, -3.7033387);
        GeoPosition Francia = new GeoPosition(46.227638, 2.213749);
        GeoPosition Alemania = new GeoPosition(51.165691, 10.451526);
        GeoPosition Austria = new GeoPosition(47.516231, 14.550072);
        GeoPosition Belgica = new GeoPosition(50.503887, 4.469936);
        GeoPosition Finlandia = new GeoPosition(61.92411, 25.748151);
        GeoPosition Italia = new GeoPosition(41.87194, 12.56738);
        GeoPosition EstadosUnidos = new GeoPosition(37.09024, -95.712891);
        GeoPosition Noruega = new GeoPosition(60.472024, 8.468946);
        GeoPosition PaisesBajos = new GeoPosition(52.132633, 5.291266);
        GeoPosition Eslovenia = new GeoPosition(46.151241, 14.995463);
        GeoPosition Suecia = new GeoPosition(60.128161, 18.643501);

        // Set the focus
        mapViewer.setZoom(14);
        mapViewer.setAddressLocation(centromapa);

        // Add interactions
        MouseInputListener mia = new PanMouseInputListener(mapViewer);
        mapViewer.addMouseListener(mia);
        mapViewer.addMouseMotionListener(mia);
        mapViewer.addMouseListener(new CenterMapListener(mapViewer));
        mapViewer.addMouseWheelListener(new ZoomMouseWheelListenerCenter(mapViewer));
        mapViewer.addKeyListener(new PanKeyListener(mapViewer));

        // Create waypoints from the geo-positions
        Set<SwingWaypoint> waypoints = new HashSet<SwingWaypoint>(Arrays.asList(
                new SwingWaypoint("España", España),
                new SwingWaypoint("Francia", Francia),
                new SwingWaypoint("Alemania", Alemania),
                new SwingWaypoint("Austria", Austria),
                new SwingWaypoint("Bélgica", Belgica),
                new SwingWaypoint("Finlandia", Finlandia),
                new SwingWaypoint("Italia", Italia),
                new SwingWaypoint("Estados Unidos", EstadosUnidos),
                new SwingWaypoint("Noruega", Noruega),
                new SwingWaypoint("Países Bajos", PaisesBajos),
                new SwingWaypoint("Eslovenia", Eslovenia),
                new SwingWaypoint("Suecia", Suecia)));

        // Set the overlay painter
        WaypointPainter<SwingWaypoint> swingWaypointPainter = new SwingWaypointOverlayPainter();
        swingWaypointPainter.setWaypoints(waypoints);
        mapViewer.setOverlayPainter(swingWaypointPainter);

        // Add the JButtons to the map viewer
        for (SwingWaypoint w : waypoints) {
            mapViewer.add(w.getButton());
        }
        return mapViewer;
    }

    /*
    Método que muestra la lista de los paises y muestra un jOptionPane con la
    lista de paises para que los seleccione
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        try {
            if (e.getSource().toString().equals(etiquetaImagenJuego1.toString())) {
                Object pais = JOptionPane.showInputDialog(vistajuego4, "Selecciona el país", "País", JOptionPane.INFORMATION_MESSAGE, null, paises.toArray(), 0);
                if (!pais.toString().isEmpty()) {
                    etiquetaImagenJuego1.removeMouseListener(this);
                    contadorPaises++;
                    if (pais.toString().equals(respuestasPaises.get(0).toString())) {
                        respuestasCorrectasUsuario++;
                      etiquetaImagenJuego1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 2, true)), "1) Primavera", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));     
                    } else {
                      etiquetaImagenJuego1.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)), "1) Primavera", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));      
                    }
                    comprobarContador();
                }

            } else if (e.getSource().toString().equals(etiquetaImagenJuego2.toString())) {
                Object pais = JOptionPane.showInputDialog(vistajuego4, "Selecciona el país", "País", JOptionPane.INFORMATION_MESSAGE, null, paises.toArray(), 0);
                if (!pais.toString().isEmpty()) {
                    etiquetaImagenJuego2.removeMouseListener(this);
                    contadorPaises++;
                    if (pais.toString().equals(respuestasPaises.get(1).toString())) {
                        respuestasCorrectasUsuario++;
                        etiquetaImagenJuego2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 2, true)), "2) El hijo del hombre", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13))); 
                    } else {
                        etiquetaImagenJuego2.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)), "2) El hijo del hombre", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    }

                    comprobarContador();
                }

            } else if (e.getSource().toString().equals(etiquetaImagenJuego3.toString())) {
                Object pais = JOptionPane.showInputDialog(vistajuego4, "Selecciona el país", "País", JOptionPane.INFORMATION_MESSAGE, null, paises.toArray(), 0);
                if (!pais.toString().isEmpty()) {
                    etiquetaImagenJuego3.removeMouseListener(this);
                    contadorPaises++;
                    if (pais.toString().equals(respuestasPaises.get(2).toString())) {
                        respuestasCorrectasUsuario++;
                        etiquetaImagenJuego3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 2, true)), "3) El beso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    } else {
                       etiquetaImagenJuego3.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)), "3) El beso", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    }
                    comprobarContador();
                }
            } else if (e.getSource().toString().equals(etiquetaImagenJuego4.toString())) {
                Object pais = JOptionPane.showInputDialog(vistajuego4, "Selecciona el país", "País", JOptionPane.INFORMATION_MESSAGE, null, paises.toArray(), 0);
                if (!pais.toString().isEmpty()) {
                    etiquetaImagenJuego4.removeMouseListener(this);
                    contadorPaises++;
                    if (pais.toString().equals(respuestasPaises.get(3).toString())) {
                        respuestasCorrectasUsuario++;
                        etiquetaImagenJuego4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 2, true)), "4) El caminante sobre el mar de nubes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    } else {
                        etiquetaImagenJuego4.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)), "4) El caminante sobre el mar de nubes", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    }
                    comprobarContador();
                }
            } else if (e.getSource().toString().equals(etiquetaImagenJuego5.toString())) {
                Object pais = JOptionPane.showInputDialog(vistajuego4, "Selecciona el país", "País", JOptionPane.INFORMATION_MESSAGE, null, paises.toArray(), 0);
                if (!pais.toString().isEmpty()) {
                    etiquetaImagenJuego5.removeMouseListener(this);
                    contadorPaises++;
                    if (pais.toString().equals(respuestasPaises.get(4).toString())) {
                        respuestasCorrectasUsuario++;
                        etiquetaImagenJuego5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 2, true)), "5) El angel herido", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    } else {
                        etiquetaImagenJuego5.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)), "5) El angel herido", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    }
                    comprobarContador();
                }
            } else if (e.getSource().toString().equals(etiquetaImagenJuego6.toString())) {
                Object pais = JOptionPane.showInputDialog(vistajuego4, "Selecciona el país", "País", JOptionPane.INFORMATION_MESSAGE, null, paises.toArray(), 0);
                if (!pais.toString().isEmpty()) {
                    etiquetaImagenJuego6.removeMouseListener(this);
                    contadorPaises++;
                    if (pais.toString().equals(respuestasPaises.get(5).toString())) {
                        respuestasCorrectasUsuario++;
                        etiquetaImagenJuego6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 255, 0), 2, true)), "6) El fusilamiento del 3 de Mayo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    } else {
                       etiquetaImagenJuego6.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createTitledBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 0, 0), 2, true)), "6) El fusilamiento del 3 de Mayo", javax.swing.border.TitledBorder.CENTER, javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("NSimSun", 0, 13)));
                    }
                    comprobarContador();
                }
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(vistajuego4, "Operación Cancelada");
        }
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mousePressed(MouseEvent e
    ) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseReleased(MouseEvent e
    ) {
        // throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseEntered(MouseEvent e
    ) {
        //  throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void mouseExited(MouseEvent e
    ) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
