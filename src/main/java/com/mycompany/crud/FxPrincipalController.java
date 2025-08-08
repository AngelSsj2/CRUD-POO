/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package com.mycompany.crud;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author angel
 */
public class FxPrincipalController implements Initializable {

    
    @FXML
    private ComboBox<String> cbSexo = new ComboBox<>();
    @FXML
    private TextField txtNombre;
    @FXML
    private TextField txtApellidos;
    @FXML
    private TextField txtCorreo;
    @FXML
    private DatePicker dateNacimiento;
    @FXML
    private TableView<Object[]> tbUsuarios;
    @FXML
    private TextField Id;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        /*
        Clases.Conexion objetoConexion = new Clases.Conexion();
        objetoConexion.establecerConexion();*/
        Clases.Usuarios objetoUsuarios = new Clases.Usuarios();
        objetoUsuarios.MostrarSexo(cbSexo);
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        Id.setDisable(true);
    }

    @FXML
    private void guardarUsuario(ActionEvent event){
        Clases.Usuarios objetoUsuarios = new Clases.Usuarios();
        objetoUsuarios.AgregarUsuarios(txtNombre, txtApellidos, txtCorreo, dateNacimiento, cbSexo);
        tbUsuarios.getColumns().clear();
        tbUsuarios.getItems().clear();
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        objetoUsuarios.limpiarCampos(Id, txtNombre, txtApellidos, txtCorreo, dateNacimiento, cbSexo);
    }
    
    @FXML
    private void seleccionarUsuario(MouseEvent event){
        Clases.Usuarios objetoUsuarios = new Clases.Usuarios();
        objetoUsuarios.seleccionarUsuario(tbUsuarios, Id, txtNombre, txtApellidos, txtCorreo, dateNacimiento, cbSexo);
    }
    
    @FXML
    private void modificarUsuario(ActionEvent event){
        Clases.Usuarios objetoUsuarios = new Clases.Usuarios();
        objetoUsuarios.modificarUsuarios(Id, txtNombre, txtApellidos, txtCorreo, dateNacimiento, cbSexo);
        tbUsuarios.getColumns().clear();
        tbUsuarios.getItems().clear();
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        objetoUsuarios.limpiarCampos(Id, txtNombre, txtApellidos, txtCorreo, dateNacimiento, cbSexo);
    }
    
    @FXML
    private void eliminarUsuario(ActionEvent event){
        Clases.Usuarios objetoUsuarios = new Clases.Usuarios();
        objetoUsuarios.eliminarUsuario(Id);
        tbUsuarios.getColumns().clear();
        tbUsuarios.getItems().clear();
        objetoUsuarios.mostrarUsuarios(tbUsuarios);
        objetoUsuarios.limpiarCampos(Id, txtNombre, txtApellidos, txtCorreo, dateNacimiento, cbSexo);
    }
    
    
    
}
