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
        txtResultado.setText("");
        lblEstado.setText("Generando...");

        try (Connection conn = DriverManager.getConnection(connectionString, userDB, passDB)) {
            String sqlInsert = "{ call PR_INSERTAR_SERIE_4(?) }";
            CallableStatement cs = conn.prepareCall(sqlInsert);

            for (int i = 0; i <= 3862; i += 4) {
                cs.setInt(1, i);
                cs.execute();
                txtResultado.append(i + ", ");
            }

            lblEstado.setText("Serie generada y almacenada ✔");
        } catch (Exception ex) {
            lblEstado.setText("Error al insertar ❌");
            ex.printStackTrace();
        }
    }

    private void consultarSuma(ActionEvent e) {
        try (Connection conn = DriverManager.getConnection(connectionString, userDB, passDB)) {
            String sqlConsulta = "{ call PR_CONSULTAR_SUMA_SERIE4(?) }";
            CallableStatement cs = conn.prepareCall(sqlConsulta);
            cs.registerOutParameter(1, Types.NUMERIC);
            cs.execute();

            long suma = cs.getLong(1);
            txtResultado.setText("📌 La suma total consultada desde Oracle es:\n\n" + suma);

            lblEstado.setText("Consulta realizada ✔");
        } catch (Exception ex) {
            lblEstado.setText("Error en consulta ❌");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormSerie4BD().setVisible(true));
    }
}
