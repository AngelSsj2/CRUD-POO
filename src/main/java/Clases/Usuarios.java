/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Clases;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javax.xml.transform.Result;
/**
 *
 * @author angel
 */
public class Usuarios {
    
    public void MostrarSexo(ComboBox<String> comboSexo){
        Clases.Conexion objetoConexion = new Clases.Conexion();
        comboSexo.getItems().clear();
        comboSexo.setValue("Seleccione Sexo: ");
        
        String sql = "select * from sexo;";
        
        try {
            Statement st = objetoConexion.establecerConexion().createStatement();
            
            ResultSet rs = st.executeQuery(sql);
            
            while (rs.next()) {
                int idSexo = rs.getInt("id");
                String nombreSexo = rs.getString("sexo");
                
                comboSexo.getItems().add(nombreSexo);
                comboSexo.getProperties().put(nombreSexo, idSexo);
                
            }
            
        } catch (Exception e) {
            mostrarAlerta("ERROR ", "Error al mostrar los sexos: "+e.toString());
        }
        finally{
            objetoConexion.cerrarConexion();
        }
        
    }
    
    //Agregar los usuarios desde JavaFX y se agreguen al SQL
    public void AgregarUsuarios(TextField nombres, TextField apellidos, TextField correo, DatePicker nacimiento, ComboBox<String> comboSexo){
        Conexion objetoConexion = new Conexion();
        
        String consulta = "INSERT INTO usuarios (nombre, apellidos, fkSexo, fechaNac, correo) VALUES (?,?,?,?,?);";
        try (CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta)){
            
            cs.setString(1, nombres.getText());
            cs.setString(2, apellidos.getText());
            
            String nombreSexoSeleccionado = comboSexo.getSelectionModel().getSelectedItem();
            
            int idSexo = (int) comboSexo.getProperties().get(nombreSexoSeleccionado);
            
            cs.setString(5, correo.getText());
            
            LocalDate fechaSelecionada = nacimiento.getValue();
            Date fechaSQL = Date.valueOf(fechaSelecionada);
            cs.setDate(4, fechaSQL);
           
            cs.setInt(3, idSexo);
            
            cs.execute();
            
            mostrarAlerta("Informacion", "se guardo correctamente ");
            
        } catch (Exception e) {
            mostrarAlerta("Informacion", "Error al mostrar la informacion "+e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    
    //Codigo que hace que la base de datos se vea en el TableView
    public void mostrarUsuarios(TableView<Object[]> tablaTotalUsuarios) {

        Clases.Conexion objetoConexion = new Clases.Conexion();

        TableColumn<Object[], String> idColumn = new TableColumn<>("Id");
        TableColumn<Object[], String> nombresColumn = new TableColumn<>("Nombres");
        TableColumn<Object[], String> apellidosColumn = new TableColumn<>("Apellidos");
        TableColumn<Object[], String> correoColumn = new TableColumn<>("Correo");
        TableColumn<Object[], String> fechaNacColumn = new TableColumn<>("FNacimiento");
        TableColumn<Object[], String> sexoColumn = new TableColumn<>("Sexo");

        idColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[0].toString()));
        nombresColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[1].toString()));
        apellidosColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[2].toString()));
        correoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[3].toString()));
        fechaNacColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[4].toString()));
        sexoColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[5].toString()));

        tablaTotalUsuarios.getColumns().addAll(idColumn, nombresColumn, apellidosColumn, correoColumn, fechaNacColumn, sexoColumn);

        String sql = "SELECT usuarios.id,usuarios.nombre,usuarios.apellidos,sexo.sexo,usuarios.correo,usuarios.fechaNac from usuarios inner join sexo on usuarios.fkSexo = sexo.id;";
        
        try {
            
            Statement st = objetoConexion.establecerConexion().createStatement();
            ResultSet rs = st.executeQuery(sql);
            
            //Se guarden los datos en la columna de fecha de nacimiento con un formato 00/00/0000
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            
            while (rs.next()) {
                java.sql.Date fechaSql = rs.getDate("fechaNac");
                String nuevaFecha = (fechaSql != null) ? sdf.format(fechaSql): null;
                
                Object[] rowData = {
                
                rs.getString("Id"),
                rs.getString("Nombre"),
                rs.getString("Apellidos"),
                rs.getString("Correo"),
                nuevaFecha,
                rs.getString("Sexo")
            };
            
            tablaTotalUsuarios.getItems().add(rowData);
            }
            
        } catch (Exception e) {
            mostrarAlerta("Error", "Error al guardar"+e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
        
    }
    
    //Seleccionar los usuarios desde la tabla 
    public void seleccionarUsuario(TableView<Object[]> tablaTotalUsuarios, TextField id, TextField nombres, TextField apellidos, TextField correo, DatePicker nacimiento, ComboBox<String> comboSexo){
        
        int fila = tablaTotalUsuarios.getSelectionModel().getSelectedIndex();
        if (fila >= 0) {
            Object [] filaSeleccionada = tablaTotalUsuarios.getItems().get(fila);
            
            id.setText(filaSeleccionada[0].toString());
            nombres.setText(filaSeleccionada[1].toString());
            apellidos.setText(filaSeleccionada[2].toString());
            correo.setText(filaSeleccionada[3].toString());
            
            String fechaString = filaSeleccionada[4].toString();    
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate fechaLocalDate = LocalDate.parse(fechaString,formatter);
            nacimiento.setValue(fechaLocalDate);
            comboSexo.getSelectionModel().select(filaSeleccionada[5].toString());
        }
        
    }
    
    //Modificar los usuarios desde javaFX
    public void modificarUsuarios(TextField id,  TextField nombres, TextField apellidos, TextField correo, DatePicker nacimiento, ComboBox<String> comboSexo){
        Conexion objetoConexion = new Conexion();
        String consulta = "update usuarios set usuarios.nombre=?, usuarios.apellidos=?, usuarios.correo=?,  usuarios.fkSexo=?, usuarios.fechaNac=? where usuarios.id=?;";
        
        try {
            
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setString(1, nombres.getText());
            cs.setString(2, apellidos.getText());
            cs.setString(3, correo.getText());
            String nombreSexoSeleccionado = comboSexo.getSelectionModel().getSelectedItem();
            
            int idSexo = (int) comboSexo.getProperties().get(nombreSexoSeleccionado);
            cs.setInt(4, idSexo);
            
            LocalDate fechaSeleccionada = nacimiento.getValue();
            Date fechaSQL = Date.valueOf(fechaSeleccionada);
            cs.setDate(5, fechaSQL);
            
            cs.setInt(6, Integer.parseInt(id.getText()));
            cs.execute();
            
            mostrarAlerta("Informacion ", "Se modifico el registro");
            
        } catch (Exception e) {
            mostrarAlerta("Informacion ", "No se modifico el registro"+e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
        
    }
    
    public void eliminarUsuario(TextField id){
        Conexion objetoConexion = new Conexion();
        
        String consulta = "DELETE FROM usuarios WHERE usuarios.id=?";
        
        try {
            CallableStatement cs = objetoConexion.establecerConexion().prepareCall(consulta);
            cs.setInt(1,Integer.parseInt(id.getText()));
            cs.execute();
            
            mostrarAlerta("Informacion ", "Se elimino el registro");
            
        } catch (Exception e) {
            mostrarAlerta("Informacion ", "No se elimino el registro"+e.toString());
        } finally {
            objetoConexion.cerrarConexion();
        }
    }
    
    private void mostrarAlerta(String tittle, String content){
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(tittle);
        alerta.setHeaderText(null);
        alerta.setContentText(content);
        alerta.showAndWait();
    }
    
    public void limpiarCampos (TextField id,  TextField nombres, TextField apellidos, TextField correo, DatePicker nacimiento, ComboBox<String> comboSexo){
        
        id.setText("");
        nombres.setText("");
        apellidos.setText("");
        correo.setText("");
        nacimiento.setValue(LocalDate.now());
        
    }
    
}
