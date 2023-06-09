/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.gf.proyecto.app;

import com.gf.proyecto.controlador.ControladorVistaPrincipal;
import com.gf.proyecto.modelo.Ranking;
import com.gf.proyecto.vistas.VistaPrincipal;

/**
 *
 * @author Lucia
 */
public class AppJuegosMVC { 

    public static void main(String[] args) {
        Ranking modeloRank = new Ranking();
        VistaPrincipal vistaPrincipal = new VistaPrincipal();
        ControladorVistaPrincipal ct = new ControladorVistaPrincipal(modeloRank, vistaPrincipal);
        ct.iniciarVista();

    }
}
