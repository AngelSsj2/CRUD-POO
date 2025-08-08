/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.Connection;
import java.sql.DriverManager;
import javafx.scene.control.Alert;
/**
 *
 * @author angel
 */
public class Conexion {
    Connection conectar = null;
    
    // nombre de usuario de nuestra base de datos
    String usuario = "root";
    // Contraseña de nuestro usuario
    String contrasenia = "1234";
    //Nombre de nuestra base de datos
    String db = "dbUsuarios";
    // Localhost
    String ip = "localhost";
    //Puerto del SQL
    String puerto = "3306";
    
    String cadena = "jdbc:mysql://"+ip+":"+puerto+"/"+db;
    
    public Connection establecerConexion(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conectar = DriverManager.getConnection(cadena, usuario,contrasenia);
            // mostrarAlerta("Mensaje: ", "Se conecto la base de datos");
            
        } catch (Exception e) {
            mostrarAlerta("Mensaje: ", "No se conecto la base de datos, error "+e.toString());
        }
        
        return conectar;
    }
    
    private void mostrarAlerta(String tittle, String content){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(tittle);
        alerta.setHeaderText(null);
        alerta.setContentText(content);
        alerta.showAndWait();
    }
    
    public void cerrarConexion(){
        try {
            if (conectar!= null && !conectar.isClosed()) {
                conectar.close();
                // mostrarAlerta("Mensaje ", "Conexion Cerrada");
            }
        } catch (Exception e) {
            mostrarAlerta("Mensaje ", "Error al cerrar la conexion");
        }
    }
    
}
