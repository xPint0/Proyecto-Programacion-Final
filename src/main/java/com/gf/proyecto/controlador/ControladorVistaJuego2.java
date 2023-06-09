/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.controlador;

import com.gf.proyecto.utilidades.ConexionBD;
import com.gf.proyecto.vistas.VistaJuego2;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucia
 */
public class ControladorVistaJuego2 implements ActionListener {

    private final VistaJuego2 vistajuego2;
    protected long tiempoInicioJuego2; //Variable que almacena el momento inicial de arranque del juego
    protected String tiempoJuego2; //Variable convierte a String el tiempo total del juego
    protected int respuestasCorrectasUsuario; //Variable que almacena las respuestas correctas del usuario
    protected int contMuseos = 0; //Contador que indica la posicion del museo actual
    protected List museos;  //Lista que almacena los nombres de los museos
    protected List<Boolean> esReal; //Lista que almacena si existe o no el museo (true/false)
    protected List imagenes; //Lista que almacena la url de las imágenes del juego

    public ControladorVistaJuego2(VistaJuego2 vistajuego2) {
        this.vistajuego2 = vistajuego2;
    }

    /*
    Método que carga la vista inicial del juego, junto con los datos de las imágenes 
    y de los museos e iniciar el timer 
     */
    public void iniciarVistaJuego2() {
        imagenes = new ArrayList();
        museos = new ArrayList();
        esReal = new ArrayList();
        try {
            recuperarDatos();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVistaJuego2.class.getName()).log(Level.SEVERE, null, ex);
        }
        cambiarEtiqueta();
        iniciarComponentesJuego2();
        vistajuego2.setSize(establecerTamanioVentana(0.5, 0.5));
        vistajuego2.setResizable(false);
        vistajuego2.setLocationRelativeTo(null);
        vistajuego2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistajuego2.setVisible(true);
        JOptionPane.showMessageDialog(vistajuego2, "En este juego debes de seleccionar Verdadero o Falso si crees que el museo existe o no", "Verdadero o falso", JOptionPane.INFORMATION_MESSAGE);
        inicioTimer();

    }

    /*
    Método que añade a los botones el evento ActionListener
     */
    public void iniciarComponentesJuego2() {

        vistajuego2.getBotonVerdadero().addActionListener(this);
        vistajuego2.getBotonFalso().addActionListener(this);

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

    /* 
    Método que almacena en la variable el tiempo inicial
     */
    private void inicioTimer() {
        tiempoInicioJuego2 = System.nanoTime();
    }

    /*
    Método que finaliza el timer y muestra las respuestas correctas del usuario
     */
    private void finTimer() {
        JOptionPane.showMessageDialog(vistajuego2, "Has acertado " + respuestasCorrectasUsuario + " de 6", "Resultados Juego", JOptionPane.INFORMATION_MESSAGE);
        long tiempoFin = System.nanoTime();
        long tiempoJuego2segundos = (tiempoFin - tiempoInicioJuego2) / 1000000000;
        long horas = tiempoJuego2segundos / 3600;
        long minutos = (tiempoJuego2segundos % 3600) / 60;
        long segundos = (tiempoJuego2segundos % 3600) % 60;
        tiempoJuego2 = String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    /*
    Método que cambía la etiqueta del juego dependiendo la posición del contador
    y reescala las imagenes al tamaño y asigna la imagen a la etiqueta
     */
    private void cambiarEtiqueta() {
        this.vistajuego2.getEtiquetaMuseo().setText(museos.get(contMuseos).toString());
        ImageIcon imagen = new ImageIcon(imagenes.get(contMuseos).toString());
        Image reescalar = imagen.getImage().getScaledInstance(this.vistajuego2.getEtiquetaImagen().getWidth(), this.vistajuego2.getEtiquetaImagen().getHeight(), Image.SCALE_SMOOTH);
        this.vistajuego2.getEtiquetaImagen().setIcon(new ImageIcon(reescalar));
    }

    /*
    Método que formatea el tiempo final del juego y devuelve un LocalTime
     */
    public LocalTime tiempoFinal() {
        return LocalTime.parse(tiempoJuego2, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /*
    Método que inicia el juego, comprueba si la respuesta es correcta incrementando el contador,
    llama al método cambiarEtiqueta y llame al finTimer para finalizar el contador del tiempo y
    finaliza el juego cuando el contador de museos llegue al tamaño de la lista
     */
    private void juegoAutomatico(boolean resp) {

        if (resp == esReal.get(contMuseos)) {
            respuestasCorrectasUsuario++;
            contMuseos++;
            vistajuego2.getEtiquetaNumeroRespuestasCorrectas().setText(respuestasCorrectasUsuario + "/6");
            if (contMuseos == museos.size()) {
                finTimer();
                try {
                    ControladorVistaPrincipal.recuperarResultadosJuego2(tiempoFinal(), respuestasCorrectasUsuario);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorVistaJuego2.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.vistajuego2.dispose();
            } else {
                cambiarEtiqueta();
            }
        } else {
            contMuseos++;
            if (contMuseos == museos.size()) {
                finTimer();
                try {
                    ControladorVistaPrincipal.recuperarResultadosJuego2(tiempoFinal(), respuestasCorrectasUsuario);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorVistaJuego2.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.vistajuego2.dispose();
            } else {
                cambiarEtiqueta();
            }
        }
    }

    /*
    Método que recupera los datos las imagenes y los museos de la BD y establece
    la respuesta correcta
     */
    public void recuperarDatos() throws SQLException {
        Connection con = ConexionBD.getConnection();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * from museos where id_museo in(24,25,26,27,28,29)")) {
            while (rs.next()) {
                imagenes.add(rs.getString("imagen"));
                museos.add(rs.getString("nombre_museo"));
                if (rs.getString("id_pais") != null) {
                    esReal.add(true);
                } else {
                    esReal.add(false);
                }
            }
        }

    }

    /*
    Método que muestra un mensaje para confirmar la respuesta del usuario y llama
    al método juegoAutomatico para cambiar los datos y comprobrar las respuestas
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistajuego2.getBotonFalso()) {
            juegoAutomatico(false);
        } else if (e.getSource() == vistajuego2.getBotonVerdadero()) {
            juegoAutomatico(true);
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
