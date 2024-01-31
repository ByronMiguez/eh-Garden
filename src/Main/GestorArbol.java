package Main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Clases.Arbol;

public class GestorArbol {

	public static void main(String[] args) {
	
		String host = "localhost";
		String BBDD = "eh_garden";
		String usuario = "root";
		String contrasenia = "";
		
		Scanner escanea = new Scanner(System.in);
		String opcion ="";
		ArrayList<Arbol> arbolLista = new ArrayList<>();
		
		//cargar la libreria mysql conector
				try {
					Class.forName("com.mysql.cj.jdbc.Driver");
				
				//crear conexion
				Connection conexion = DriverManager.getConnection("jdbc:mysql://" + host + "/" + BBDD, usuario, contrasenia );
				//crear statement
				Statement st = conexion.createStatement();
				
				//ejecutar la consulta y recibir el resultado
				ResultSet resultado = st.executeQuery("select * from arboles" );
				}
				
				catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
	}

}
