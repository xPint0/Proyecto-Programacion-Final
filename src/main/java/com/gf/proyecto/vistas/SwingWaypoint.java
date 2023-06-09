package com.gf.proyecto.vistas;

import org.jxmapviewer.viewer.DefaultWaypoint;
import org.jxmapviewer.viewer.GeoPosition;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


/**
 * A waypoint that is represented by a button on the map.
 *
 * @author Daniel Stahr
 */
public class SwingWaypoint extends DefaultWaypoint {

    private final JButton button;
    private final String text;
    /*
    Esta clase se va a encargar de la creación los botones y controlar 
    el evento al hacer clic sobre estos. 
    */
    public SwingWaypoint(String text, GeoPosition coord) {
        super(coord);        
        this.text = text;
        button = new JButton(new ImageIcon(".\\src\\main\\java\\recursos\\waypoint_white.png"));
        button.setSize(20, 34);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(20, 34));
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JOptionPane.showMessageDialog(button, "Has pulsado en " + text+"");
            }
        });
        button.setVisible(true);
    }

    public  JButton getButton() {
        return button;
    }
}
