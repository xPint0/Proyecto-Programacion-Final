/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.controlador;

import com.gf.proyecto.utilidades.ConexionBD;
import com.gf.proyecto.vistas.VistaJuego3;
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
public class ControladorVistaJuego3 implements ActionListener {

    private final VistaJuego3 vistajuego3;
    protected long tiempoInicioJuego3;  //Variable que almacena el momento inicial de arranque del juego
    protected String tiempoJuego3;  //Variable convierte a String el tiempo total del juego
    protected int respuestasCorrectasUsuario; //Variable que almacena las respuestas correctas del usuario
    protected int contImagenes = 0; //Contador que indica la posicion de la imagen actual
    protected List<Boolean> respuestasUsuario; //Variable que almacena las respuestas correctas del usuario
    protected List<Boolean> esGregorio; //Lista que almacena si la obra es de Gregorio Fernández (true/false)
    protected List imagenesJuego3; //Lista que almacena la url de las imágenes del juego

    public ControladorVistaJuego3(VistaJuego3 vistajuego3) {

        this.vistajuego3 = vistajuego3;
    }

    /*
    Método que carga la vista inicial del juego, junto con los datos de las imágenes 
     e inicia el timer 
     */
    public void iniciarVistaJuego3() {
        imagenesJuego3 = new ArrayList();
        esGregorio = new ArrayList();
        respuestasUsuario = new ArrayList();
        try {
            recuperarDatosJuego3();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVistaJuego3.class.getName()).log(Level.SEVERE, null, ex);
        }
        cambiarEtiqueta();
        iniciarComponentes();
        vistajuego3.setSize(establecerTamanioVentana(0.7, 0.7));
        vistajuego3.setResizable(false);
        vistajuego3.setLocationRelativeTo(null);
        vistajuego3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);       
        vistajuego3.setVisible(true);
        JOptionPane.showMessageDialog(vistajuego3, "En este juego debes de seleccionar Verdadero o Falso si crees que la Obra es de Gregorio Fernández", "Gregorio Fernández", JOptionPane.INFORMATION_MESSAGE);
        inicioTimer();

    }

    /*
    Método que añade a los botones el evento ActionListener
     */
    public void iniciarComponentes() {

        vistajuego3.getBotonVerdadero().addActionListener(this);
        vistajuego3.getBotonFalso().addActionListener(this);

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
        tiempoInicioJuego3 = System.nanoTime();
    }

    /*
    Método que finaliza el timer y muestra las respuestas correctas del usuario
     */
    private void finTimer() {
        JOptionPane.showMessageDialog(vistajuego3, "Has acertado " + respuestasCorrectasUsuario + " de 13", "Resultados Juego", JOptionPane.INFORMATION_MESSAGE);
        long tiempoFin = System.nanoTime();
        long tiempoJuego2segundos = (tiempoFin - tiempoInicioJuego3) / 1000000000;
        long horas = tiempoJuego2segundos / 3600;
        long minutos = (tiempoJuego2segundos % 3600) / 60;
        long segundos = (tiempoJuego2segundos % 3600) % 60;
        tiempoJuego3 = String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }

    /*
    Método que cambía la etiqueta del juego dependiendo la posición del contador
    y reescala las imagenes al tamaño y asigna la imagen a la etiqueta
     */
    private void cambiarEtiqueta() {
        ImageIcon imagen = new ImageIcon(imagenesJuego3.get(contImagenes).toString());
        Image reescalar = imagen.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        this.vistajuego3.getEtiquetaImagen().setIcon(new ImageIcon(reescalar));
    }

    /*
    Método que inicia el juego, comprueba si la respuesta es correcta incrementando el contador,
    llama al método cambiarEtiqueta y llame al finTimer para finalizar el contador del tiempo y
    finaliza el juego cuando el contador de museos llegue al tamaño de la lista
     */
    private void iniciarJuego3(boolean resp) {
        if (resp == esGregorio.get(contImagenes)) {
            respuestasCorrectasUsuario++;
            contImagenes++;
            vistajuego3.getEtiquetaNumeroRespuestasCorrectas().setText(respuestasCorrectasUsuario + "/13");
            if (contImagenes == imagenesJuego3.size()) {
                finTimer();
                try {
                    ControladorVistaPrincipal.recuperarResultadosJuego3(tiempoFinal(), respuestasCorrectasUsuario);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorVistaJuego3.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.vistajuego3.dispose();
            } else {
                cambiarEtiqueta();
            }
        } else {
            contImagenes++;
            if (contImagenes == imagenesJuego3.size()) {
                finTimer();
                try {
                    ControladorVistaPrincipal.recuperarResultadosJuego3(tiempoFinal(), respuestasCorrectasUsuario);
                } catch (Exception ex) {
                    Logger.getLogger(ControladorVistaJuego3.class.getName()).log(Level.SEVERE, null, ex);
                }
                this.vistajuego3.dispose();
            } else {
                cambiarEtiqueta();
            }
        }
    }

    /*
    Método que formatea el tiempo final del juego y devuelve un LocalTime
     */
    public LocalTime tiempoFinal() {
        return LocalTime.parse(tiempoJuego3, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /*
    Método que recupera los datos las imagenes y los museos de la BD y establece
    la respuesta correcta
     */
    public void recuperarDatosJuego3() throws SQLException {

        Connection con = ConexionBD.getConnection();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * from obras where id_obra in(12,23,13,18,14,22,19,15,21,16,24,17,20)")) {
            while (rs.next()) {
                imagenesJuego3.add(rs.getString(5));
                if (rs.getInt(7) == 12) {
                    esGregorio.add(true);
                } else {
                    esGregorio.add(false);
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
        if (e.getSource() == vistajuego3.getBotonFalso()) {
                iniciarJuego3(false);
        } else if (e.getSource() == vistajuego3.getBotonVerdadero()) {
                iniciarJuego3(true);
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
