/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.controlador;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatLightLaf;
import com.gf.proyecto.dao.RankingDAO;
import com.gf.proyecto.modelo.Ranking;
import com.gf.proyecto.vistas.VistaJuego1;
import com.gf.proyecto.vistas.VistaJuego2;
import com.gf.proyecto.vistas.VistaJuego3;
import com.gf.proyecto.vistas.VistaJuego4;
import com.gf.proyecto.vistas.VistaPrincipal;
import com.gf.proyecto.vistas.VistaRankings;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 *
 * @author Lucia
 */
public class ControladorVistaPrincipal implements ActionListener {

    private static Ranking jugador; //Va a instanciar el jugaor
    private static VistaPrincipal vistaPrincipal; //Va a instanciar la vista princpial
    private static LookAndFeel lookAndFeelactual; //Va a a recoger el Look and Feel actual para intercambiar el tema claro y oscuro
    //String que va a almacenar la ruta de las imagenes de los iconos y de los juegos
    private final String[] imagenesIconos = {".\\src\\main\\java\\recursos\\eclipse.png", ".\\src\\main\\java\\recursos\\luna.png",
        ".\\src\\main\\java\\recursos\\trofeo.png"};
    private final String[] imagenesJuegos = {".\\src\\main\\java\\recursos\\LaGioconda.jpg", ".\\src\\main\\java\\recursos\\verdadomentira.png",
        ".\\src\\main\\java\\recursos\\gregorio.jpg", ".\\src\\main\\java\\recursos\\mapa.png"};
    private static JLabel[] etiquetas; //Array que va a contener las etiquetas
    //Variables que van a almacenar los tiempos de los juegos y los puntos
    private static LocalTime tiempoJuego1;
    private static LocalTime tiempoJuego2;
    private static LocalTime tiempoJuego3;
    private static LocalTime tiempoJuego4;
    private static int puntosJuego1;
    private static int puntosJuego2;
    private static int puntosJuego3;
    private static int puntosJuego4;

    public ControladorVistaPrincipal(Ranking jugador, VistaPrincipal vistaPrincipal) {
        ControladorVistaPrincipal.jugador = jugador;
        ControladorVistaPrincipal.vistaPrincipal = vistaPrincipal;
    }

    /**
     * Método que va a iniciar la vista
     */
    public void iniciarVista() {
        iniciarComponentes();
        establecerTemaClaro();
        vistaPrincipal.setSize(establecerTamanioVentana(0.7, 0.7));
        vistaPrincipal.setResizable(false);
        vistaPrincipal.setLocationRelativeTo(null);
        vistaPrincipal.setTitle("Desarrollado por: Daniel Pinto y Lucía García");
        vistaPrincipal.setVisible(true);
        pedirNombre();
    }

    /*
    Método que añade a los botones el evento ActionListener, va a establecer 
    los tooltip y también las imágenes y las etiquetas
     */
    public void iniciarComponentes() {
        vistaPrincipal.getBotonRanking().setToolTipText("Ver Ranking");
        vistaPrincipal.getBotonRanking().setIcon(new ImageIcon(imagenesIconos[2]));
        establecerEtiquetasImagenes();
        establecerImagenes();
        vistaPrincipal.getBotonCambiarTema().addActionListener(this);
        vistaPrincipal.getBotonJuego1().addActionListener(this);
        vistaPrincipal.getBotonJuego2().addActionListener(this);
        vistaPrincipal.getBotonJuego3().addActionListener(this);
        vistaPrincipal.getBotonJuego4().addActionListener(this);
        vistaPrincipal.getBotonRanking().addActionListener(this);
        vistaPrincipal.getBotonJuego1().setToolTipText("En este juego debes de seleccionar en el desplegable el nombre y autor de la obra mostrada");
        vistaPrincipal.getBotonJuego2().setToolTipText("En este juego debes de seleccionar Verdadero o Falso si crees que el museo existe o no");
        vistaPrincipal.getBotonJuego3().setToolTipText("En este juego debes de seleccionar Verdadero o Falso si crees que la Obra es de Gregorio Fernández");
        vistaPrincipal.getBotonJuego4().setToolTipText("En este juego debes de  pulsar en la imágenes y se abrirá un desplegable en el que podrás seleccionar el país");

    }

    /*
    Método que va a devolver el Look and Feel actual
     */
    public LookAndFeel getLookAndFeelactual() {
        return lookAndFeelactual;
    }

    /*
    Método que va a instanciar el jugador al iniciar la vista principal
     */
    public void pedirNombre() {
        try {
            String nombre = JOptionPane.showInputDialog(vistaPrincipal, "Introduce tu nombre de jugador: ");
            if (!nombre.isEmpty() && !nombre.isBlank()) {
                if (!RankingDAO.existeJugadorPorNombre(nombre)) {
                    jugador = new Ranking();
                    jugador.setJugador(nombre);
                    jugador.setPuntos(0);
                    jugador.setTiempo(LocalTime.of(0, 0, 0));
                    try {
                        RankingDAO.insertarJugador(jugador);
                    } catch (Exception ex) {
                        Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    JOptionPane.showMessageDialog(vistaPrincipal, "Debes introducir un nombre válido, ese nombre ya esta ocupado", "Nombre existente", JOptionPane.ERROR_MESSAGE);
                    pedirNombre();
                }

            } else {
                JOptionPane.showMessageDialog(vistaPrincipal, "El nombre del jugador no puede estar vacío", "Nombre vacío", JOptionPane.ERROR_MESSAGE);
                pedirNombre();
            }
        } catch (java.lang.NullPointerException ex) {
            JOptionPane.showMessageDialog(vistaPrincipal, "Operación Cancelada, se cerrará el programa", "Operación cancelada", JOptionPane.WARNING_MESSAGE);
            vistaPrincipal.dispose();
        } catch (Exception ex) {
            Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
    Método que va a establer las diferentes etiquetas y las va a incorporar en el array
     */
    public void establecerEtiquetasImagenes() {
        JLabel etiquetaImagenJuego1 = vistaPrincipal.getEtiquetaImagenJuego1();
        JLabel etiquetaImagenJuego2 = vistaPrincipal.getEtiquetaImagenJuego2();
        JLabel etiquetaImagenJuego3 = vistaPrincipal.getEtiquetaImagenJuego3();
        JLabel etiquetaImagenJuego4 = vistaPrincipal.getEtiquetaImagenJuego4();
        etiquetas = new JLabel[]{etiquetaImagenJuego1, etiquetaImagenJuego2, etiquetaImagenJuego3, etiquetaImagenJuego4};
    }

    /*
    Método que va a establecer el tema claro
     */
    public void establecerTemaClaro() {
        try {
            vistaPrincipal.getBotonCambiarTema().setToolTipText("Cambiar tema oscuro");
            vistaPrincipal.getBotonCambiarTema().setIcon(new ImageIcon(imagenesIconos[0]));
            UIManager.setLookAndFeel(new FlatLightLaf());
            lookAndFeelactual = UIManager.getLookAndFeel();
            SwingUtilities.updateComponentTreeUI(vistaPrincipal);
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println(ex.getMessage());
        }

    }

    /*
    Método que va a establecer el tema claro
     */
    public void establecerTemaOscuro() {
        try {
            vistaPrincipal.getBotonCambiarTema().setToolTipText("Cambiar tema claro");
            vistaPrincipal.getBotonCambiarTema().setIcon(new ImageIcon(imagenesIconos[1]));
            UIManager.setLookAndFeel(new FlatDarculaLaf());
            lookAndFeelactual = UIManager.getLookAndFeel();
            SwingUtilities.updateComponentTreeUI(vistaPrincipal);
        } catch (UnsupportedLookAndFeelException ex) {
            System.err.println(ex.getMessage());
        }
    }

    /*
    Método que establece el tamaño de la ventana dependiendo de la resolución
    de la pantalla
     */
    public Dimension establecerTamanioVentana(double ancho, double alto) {
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
    Método que va a recuperar el tiempo y los puntos del juego 1 y los va a añadir
    al ranking del jugador 
    */
    public static void recuperarResultadosJuego1(LocalTime tiempo, int puntos) throws Exception {
        tiempoJuego1 = tiempo;
        puntosJuego1 = puntos;
        JOptionPane.showMessageDialog(null, "Duración: " + tiempoJuego1 + "\nPuntuación Obtenida: " + puntosJuego1);
        int resp = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el resultado?", "Guardar resultado", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            jugador.setPuntos(jugador.getPuntos() + puntosJuego1);
            jugador.setTiempo(jugador.getTiempo().plusNanos(tiempoJuego1.toNanoOfDay()));
            RankingDAO.modificarJugador(jugador, jugador.getJugador());
        }
        vistaPrincipal.getBotonJuego1().setEnabled(false);
        vistaPrincipal.getBotonJuego1().setToolTipText("No puedes volver a jugar, ya has jugado");
    }
/*
    Método que va a recuperar el tiempo y los puntos del juego 2 y los va a añadir
    al ranking del jugador 
    */
    public static void recuperarResultadosJuego2(LocalTime tiempo, int puntos) throws Exception {
        tiempoJuego2 = tiempo;
        puntosJuego2 = puntos;
        JOptionPane.showMessageDialog(null, "Duración: " + tiempoJuego2 + "\nPuntuación Obtenida: " + puntosJuego2);
        int resp = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el resultado?", "Guardar resultado", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            jugador.setPuntos(jugador.getPuntos() + puntosJuego2);
            jugador.setTiempo(jugador.getTiempo().plusNanos(tiempoJuego2.toNanoOfDay()));
            RankingDAO.modificarJugador(jugador, jugador.getJugador());

        }
        vistaPrincipal.getBotonJuego2().setEnabled(false);
        vistaPrincipal.getBotonJuego2().setToolTipText("No puedes volver a jugar, ya has jugado");
    }
/*
    Método que va a recuperar el tiempo y los puntos del juego 3 y los va a añadir
    al ranking del jugador 
    */
    public static void recuperarResultadosJuego3(LocalTime tiempo, int puntos) throws Exception {
        tiempoJuego3 = tiempo;
        puntosJuego3 = puntos;
        JOptionPane.showMessageDialog(null, "Duración: " + tiempoJuego3 + "\nPuntuación Obtenida: " + puntosJuego3);
        int resp = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el resultado?", "Guardar resultado", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            jugador.setPuntos(jugador.getPuntos() + puntosJuego3);
            jugador.setTiempo(jugador.getTiempo().plusNanos(tiempoJuego3.toNanoOfDay()));
            RankingDAO.modificarJugador(jugador, jugador.getJugador());
        }
        vistaPrincipal.getBotonJuego3().setEnabled(false);
        vistaPrincipal.getBotonJuego3().setToolTipText("No puedes volver a jugar, ya has jugado");
    }
/*
    Método que va a recuperar el tiempo y los puntos del juego 4 y los va a añadir
    al ranking del jugador 
    */
    public static void recuperarResultadosJuego4(LocalTime tiempo, int puntos) throws Exception {
        tiempoJuego4 = tiempo;
        puntosJuego4 = puntos;
        JOptionPane.showMessageDialog(null, "Duración: " + tiempoJuego4 + "\nPuntuación Obtenida: " + puntosJuego4);
        int resp = JOptionPane.showConfirmDialog(null, "¿Quieres guardar el resultado?", "Guardar resultado", JOptionPane.YES_NO_OPTION);
        if (resp == JOptionPane.YES_OPTION) {
            jugador.setPuntos(jugador.getPuntos() + puntosJuego4);
            jugador.setTiempo(jugador.getTiempo().plusNanos(tiempoJuego4.toNanoOfDay()));
            RankingDAO.modificarJugador(jugador, jugador.getJugador());
        }
        vistaPrincipal.getBotonJuego4().setEnabled(false);
        vistaPrincipal.getBotonJuego4().setToolTipText("No puedes volver a jugar, ya has jugado");
    }
    /*
    Método que va a reescalar y establecer las diferentes imágenes en sus respectivas etiquetas
    */
    public void establecerImagenes() {
        ImageIcon imagen;
        Image imagenEscalada;
        JLabel etiqueta;
        for (int i = 0; i < imagenesJuegos.length; i++) {
            imagen = new ImageIcon(imagenesJuegos[i]);
            etiqueta = etiquetas[i];
            imagenEscalada = imagen.getImage().getScaledInstance(etiqueta.getWidth(), etiqueta.getHeight(), Image.SCALE_SMOOTH);
            etiqueta.setIcon(new ImageIcon(imagenEscalada));
        }
    }
    /*
    Método que va iniciar los controladores de las vistas de los diferentes juegos,
    ranking y también el cambio de tema claro a oscuro y vicerversa
    */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == vistaPrincipal.getBotonCambiarTema()) {
            if (vistaPrincipal.getBotonCambiarTema().isSelected()) {
                establecerTemaClaro();
            } else {
                establecerTemaOscuro();
            }
        } else if (e.getSource() == vistaPrincipal.getBotonJuego1()) {
            if (tiempoJuego1 == null) {
                VistaJuego1 vistaJuego1 = new VistaJuego1();
                ControladorVistaJuego1 ctJuego1 = new ControladorVistaJuego1(vistaJuego1);
                ctJuego1.iniciarVistaJuego1();
            }

        } else if (e.getSource() == vistaPrincipal.getBotonJuego2()) {
            if (tiempoJuego2 == null) {
                VistaJuego2 vistaJuego2 = new VistaJuego2();
                ControladorVistaJuego2 ctJuego2 = new ControladorVistaJuego2(vistaJuego2);
                ctJuego2.iniciarVistaJuego2();
            } else {
                vistaPrincipal.getBotonJuego2().setEnabled(false);
            }
        } else if (e.getSource() == vistaPrincipal.getBotonJuego3()) {
            if (tiempoJuego3 == null) {
                VistaJuego3 juego3vista = new VistaJuego3();
                ControladorVistaJuego3 ctJuego3 = new ControladorVistaJuego3(juego3vista);
                ctJuego3.iniciarVistaJuego3();
            } else {
                vistaPrincipal.getBotonJuego3().setEnabled(false);
            }
        } else if (e.getSource() == vistaPrincipal.getBotonJuego4()) {
            if (tiempoJuego4 == null) {
                VistaJuego4 juego4vista = new VistaJuego4();
                ControladorVistaJuego4 ctJuego4 = new ControladorVistaJuego4(juego4vista);
                ctJuego4.iniciarVistaJuego4();
            } else {
                vistaPrincipal.getBotonJuego4().setEnabled(false);
            }

        } else if (e.getSource() == vistaPrincipal.getBotonRanking()) {
            try {
                VistaRankings juegoRanking = new VistaRankings();
                ControladorVistaRanking ctVistaRanking = new ControladorVistaRanking(juegoRanking);
                ctVistaRanking.iniciarVistaRanking();

            } catch (Exception ex) {
                Logger.getLogger(ControladorVistaPrincipal.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
