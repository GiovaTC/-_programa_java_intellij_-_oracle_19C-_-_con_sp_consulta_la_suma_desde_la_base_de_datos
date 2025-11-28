# -_programa_java_intellij_-_oracle_19C-_-_con_sp_consulta_la_suma_desde_la_base_de_datos :. 

<img width="1536" height="1024" alt="image" src="https://github.com/user-attachments/assets/ea25d321-0e91-49f0-8da7-627d2f690e2b" />  

# 🚀 Programa Java (IntelliJ) + Oracle 19c

📌 **Sistema completo con procedimiento almacenado que consulta la suma
directamente desde la base de datos.**

------------------------------------------------------------------------

## 🧮 1. Lógica del sistema

✔ El programa genera la serie de 4 en 4 hasta llegar a 3862\
✔ Inserta cada valor en la tabla `SERIE4_LOG`\
✔ Ejecuta un SP que suma los registros en la base de datos\
✔ Muestra el resultado en interfaz gráfica Swing

------------------------------------------------------------------------

## 🛢️ 2. Script SQL Oracle -- Tabla + Secuencia + SP

``` sql
CREATE TABLE SERIE4_LOG (
    ID_LOG NUMBER PRIMARY KEY,
    VALOR NUMBER,
    FECHA_REGISTRO DATE
);

CREATE SEQUENCE SERIE4_LOG_SEQ START WITH 1 INCREMENT BY 1;

CREATE OR REPLACE PROCEDURE PR_INSERTAR_SERIE4 (
    p_valor IN NUMBER
) AS
BEGIN
    INSERT INTO SERIE4_LOG (ID_LOG, VALOR, FECHA_REGISTRO)
    VALUES (SERIE4_LOG_SEQ.NEXTVAL, p_valor, SYSDATE);
    COMMIT;
END;
/

CREATE OR REPLACE PROCEDURE PR_CONSULTAR_SUMA_SERIE4 (
    p_suma OUT NUMBER
) AS
BEGIN
    SELECT SUM(VALOR) INTO p_suma FROM SERIE4_LOG;
END;
/
```

------------------------------------------------------------------------

## ☕ 3. Código Java Completo (Swing + Oracle JDBC)

``` java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;

public class FormSerie4BD extends JFrame {

    private JTextArea txtResultado;
    private JButton btnGenerar, btnConsultar;
    private JLabel lblEstado;

    private final String connectionString = "jdbc:oracle:thin:@localhost:1521:orcl";
    private final String userDB = "SYSTEM";
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
            String sqlInsert = "{ call PR_INSERTAR_SERIE4(?) }";
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
```

------------------------------------------------------------------------

## 📦 4. Dependencia Maven

``` xml
<dependency>
    <groupId>com.oracle.database.jdbc</groupId>
    <artifactId>ojdbc8</artifactId>
    <version>19.3.0.0</version>
</dependency>
```

------------------------------------------------------------------------

## 🔍 5. Validación rápida

``` sql
SELECT * FROM SERIE4_LOG ORDER BY ID_LOG DESC;
```

------------------------------------------------------------------------

## 📌 6. Ejecución

1️⃣ Compilar el proyecto\
2️⃣ Ejecutar la interfaz gráfica\
3️⃣ Click en **Generar Serie e Insertar**\
4️⃣ Click en **Consultar Suma SP**

------------------------------------------------------------------------

## ✨ Resultado esperado

    📌 La suma total consultada desde Oracle es:
    3720456

------------------------------------------------------------------------

## 💡 Autor

👨‍💻 **Giovanny Alejandro Tapiero Cataño**\
📍 Colombia

------------------------------------------------------------------------ :. .
