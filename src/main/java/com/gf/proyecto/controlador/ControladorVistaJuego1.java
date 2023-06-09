/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.controlador;

import com.gf.proyecto.utilidades.ConexionBD;
import com.gf.proyecto.vistas.VistaJuego1;
import com.gf.proyecto.vistas.VistaPrincipal;
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
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Lucia
 */
public class ControladorVistaJuego1 implements ActionListener {

    private final VistaJuego1 vistajuego1;
    private long tiempoInicioJuego1; //Variable que almacena el momento inicial de arranque del juego
    private String tiempoJuego1;  //Variable convierte a String el tiempo total del juego
    private int contObras = 0; //Contador que indica la posicion del museo actual
    private int respuestasCorrectasUsuario;  //Variable que almacena las respuestas correctas del usuario
    private List imagenesJuego1; //Lista que almacena la url de las imágenes del juego
    //Array de tipo String que almacena las opciones de cada imágen del juego
    private final String[] opciones1 = {"La cena de Jesús (René Magritte)", "La última cena (Leonardo Da Vinci)", "La cena de la historia (Leonardo Da Vinci)", "Jesus y 8 más (Francisco de Goya)"};
    private final String[] opciones2 = {"La Noche (Ivan Grohar)", "La noche oscura (Hugo Simberg)", "La noche estrellada (Vicent Van Gogh)", "Las estrellas de la noche (Vicent Van Gogh)"};
    private final String[] opciones3 = {"El grito (Edvard Munch)", "El hombre que grita (Johannes Vermeer)", "El feo (Edvard Munch)", "El feo que grita (Leonardo Da Vinci)"};
    private final String[] opciones4 = {"La guerra (José María Gutierrez)", "El horror de la guerra (Francisco de Goya)", "El bombardeo de Guernica (Pablo Picasso)", "Guernica (Pablo Picasso)"};
    private final String[] opciones5 = {"La chica de la perla (Johannes Vermeer)", "La Joven de la Perla (Johannes Vermeer)", "La joven (Gustav Klimt)", "La joven hermosa (Edvard Much)"};
    private final Integer[] respCorrectas = {1, 2, 0, 3, 1}; //Array que almacena las respuestas correctas
    private final Integer[] respUsuario = new Integer[5];//Variable que almacena las respuestas correctas del usuario
    private DefaultComboBoxModel modeloCombo; //Estable el modelo de opciones de cada juego
    private VistaPrincipal vistaprincipal;

    public ControladorVistaJuego1(VistaJuego1 vistajuego1) {
        this.vistajuego1 = vistajuego1;
    }

    /*
    Método que carga la vista inicial del juego, junto con los datos de las imágenes 
    y de los las obras e iniciar el timer  
     */
    public void iniciarVistaJuego1() {
        imagenesJuego1 = new ArrayList();
        vistaprincipal = new VistaPrincipal();
        try {
            recuperarDatosJuego1();
        } catch (SQLException ex) {
            Logger.getLogger(ControladorVistaJuego1.class.getName()).log(Level.SEVERE, null, ex);
        }
        iniciarComponentes();
        vistajuego1.setSize(establecerTamanioVentana(0.7, 0.7));
        vistajuego1.setResizable(false);
        vistajuego1.setLocationRelativeTo(null);
        vistajuego1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        vistajuego1.setVisible(true);
        iniciarJuego1();
        JOptionPane.showMessageDialog(vistajuego1, "En este juego debes de seleccionar en el desplegable el "
                + "nombre y autor de la obra mostrada", "¿Quién lo hizo?", JOptionPane.INFORMATION_MESSAGE);
        inicioTimer();

    }

    /*
    Método que añade a los botones el evento ActionListener
     */
    public void iniciarComponentes() {
        this.vistajuego1.getBotonSiguienteFin().addActionListener(this);
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
        tiempoInicioJuego1 = System.nanoTime();
    }

    /*
    Método que finaliza el timer y muestra las respuestas correctas del usuario
     */
    private void finTimer() {
        JOptionPane.showMessageDialog(vistajuego1, "Has acertado " + respuestasCorrectasUsuario + " de 5", "Resultados Juego", JOptionPane.INFORMATION_MESSAGE);
        long tiempoFinJuego1 = System.nanoTime();
        long tiempoJuego2segundos = (tiempoFinJuego1 - tiempoInicioJuego1) / 1000000000;
        long horas = tiempoJuego2segundos / 3600;
        long minutos = (tiempoJuego2segundos % 3600) / 60;
        long segundos = (tiempoJuego2segundos % 3600) % 60;
        tiempoJuego1 = String.format("%02d:%02d:%02d", horas, minutos, segundos);

    }

    /*
    Método que cambía la etiqueta del juego dependiendo la posición del contador
    y reescala las imagenes al tamaño y asigna la imagen a la etiqueta
     */
    private void cambiarEtiqueta() {
        ImageIcon imagen = new ImageIcon(imagenesJuego1.get(contObras).toString());
        Image reescalar = imagen.getImage().getScaledInstance(vistajuego1.getEtiquetaFotos().getWidth(), vistajuego1.getEtiquetaFotos().getHeight(), Image.SCALE_SMOOTH);
        this.vistajuego1.getEtiquetaFotos().setIcon(new ImageIcon(reescalar));
    }

    /*
    Método que formatea el tiempo final del juego y devuelve un LocalTime
     */
    public LocalTime tiempoFinal() {
        return LocalTime.parse(tiempoJuego1, DateTimeFormatter.ofPattern("HH:mm:ss"));
    }

    /*
    Método comprueba si la respuesta es correcta incrementando el contador,
 
     */
    private void comprobarRespuestas() {
        if (this.respUsuario[contObras - 1].equals(respCorrectas[contObras - 1])) {
            respuestasCorrectasUsuario++;
            vistajuego1.getEtiquetaNumeroRespuestasCorrectas().setText(respuestasCorrectasUsuario + "/5");
        }
    }

    /*
    Método que recupera los datos las imagenes y los museos de la BD y establece
    la respuesta correcta
     */
    public void recuperarDatosJuego1() throws SQLException {

        Connection con = ConexionBD.getConnection();
        try (Statement st = con.createStatement(); ResultSet rs = st.executeQuery("SELECT * from obras where id_obra in(1,2,3,4,5)")) {
            while (rs.next()) {
                imagenesJuego1.add(rs.getString("imagen"));
            }
        }

    }

    /*
    Método que inicia el juego, cambiar las opciones del combo, incrementa el contador
    de las Obras
     */
    private void iniciarJuego1() {
        switch (contObras) {
            case 0 -> {
                cambiarEtiqueta();
                modeloCombo = new DefaultComboBoxModel(opciones1);
                this.vistajuego1.getjComboBoxOpciones().setModel(modeloCombo);
                this.vistajuego1.getBotonSiguienteFin().setText("Siguiente");
                contObras++;
            }
            case 1 -> {
                cambiarEtiqueta();
                modeloCombo = new DefaultComboBoxModel(opciones2);
                this.vistajuego1.getjComboBoxOpciones().setModel(modeloCombo);
                contObras++;
            }
            case 2 -> {
                cambiarEtiqueta();
                modeloCombo = new DefaultComboBoxModel(opciones3);
                this.vistajuego1.getjComboBoxOpciones().setModel(modeloCombo);
                contObras++;
            }
            case 3 -> {
                cambiarEtiqueta();
                modeloCombo = new DefaultComboBoxModel(opciones4);
                this.vistajuego1.getjComboBoxOpciones().setModel(modeloCombo);
                contObras++;
            }
            case 4 -> {
                cambiarEtiqueta();
                modeloCombo = new DefaultComboBoxModel(opciones5);
                this.vistajuego1.getjComboBoxOpciones().setModel(modeloCombo);
                this.vistajuego1.getBotonSiguienteFin().setText("Finalizar");
                contObras++;
            }
        }
    }

    /*
    Método que muestra un mensaje para confirmar la respuesta del usuario y llama
    al juego, comprueba las respuestas y  llama a finTimer para finalizar el contador 
    del tiempo y finaliza el juego cuando el contador de museos llegue al tamaño de la lista
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistajuego1.getBotonSiguienteFin()) {
            this.respUsuario[contObras - 1] = this.vistajuego1.getjComboBoxOpciones().getSelectedIndex();
            comprobarRespuestas();
            if (contObras < 5) {
                iniciarJuego1();
            } else {
                finTimer();
                try {
                    ControladorVistaPrincipal.recuperarResultadosJuego1(tiempoFinal(), respuestasCorrectasUsuario);                 
                } catch (Exception ex) {
                    Logger.getLogger(ControladorVistaJuego1.class.getName()).log(Level.SEVERE, null, ex);
                }
                vistaprincipal.getBotonJuego1().setEnabled(false);
                this.vistajuego1.dispose();
            }
        }
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
