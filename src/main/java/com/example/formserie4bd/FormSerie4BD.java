package com.example.formserie4bd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class FormSerie4BD extends JFrame {

    private JTextArea txtResultado;
    private JButton btnGenerar, btnConsultar;
    private JLabel lblEstado;

    private final String connectionString = "jdbc:oracle:thin:@//localhost:1521/orcl";
    private final String userDB = "system";
    private final String passDB = "Tapiero123";

    public FormSerie4BD() {
        setTitle("Serie 4 en 4 - Java + Oracle");
        setSize(500, 350);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        txtResultado = new JTextArea(8, 40);
        txtResultado.setEditable(false);
        btnGenerar = new JButton("Generar Serie e Insertar");
        btnConsultar = new JButton("Consultar Suma SP");
        lblEstado = new JLabel("Estado: en espera...");

        add(new JLabel("Resultado:"));
        add(txtResultado);
        add(btnGenerar);
        add(btnConsultar);
        add(lblEstado);

        btnGenerar.addActionListener(this::generarSerie);
        btnConsultar.addActionListener(this::consultarSuma);


    }

    private void generarSerie(ActionEvent e) {

    }

    private void consultarSuma(ActionEvent e) {
    }
}
