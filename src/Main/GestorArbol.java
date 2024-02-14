package Main;

import java.awt.Menu;
import java.security.PublicKey;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

import Clases.Arbol;
import Clases.Habitat;

public class GestorArbol {

	private static final String HOST = "localhost";
	private static final String BBDD = "eh_garden2";
	private static final String USUARIO = "root";
	private static final String CONTRASENIA = "";
	private static final Scanner escanea = new Scanner(System.in);

	private static final int SALIR = 0;
	private static final int CREATE = 1;
	private static final int READ = 2;

	public static void main(String[] args) {
		run();
	}

	public static void run() {

		ArrayList<Arbol> arboles = new ArrayList<>();
		try {

			Class.forName("com.mysql.cj.jdbc.Driver");

			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + BBDD, USUARIO,
					CONTRASENIA);

			Statement st = conexion.createStatement();

			// Variables
			Arbol a;
			Habitat h;
			int id = 0;
			int select = 0;

			// Menu
			do {
				menu();
				select = Integer.parseInt(escanea.nextLine());

				switch (select) {

				case CREATE:
					a = new Arbol();
					h = new Habitat();
					System.out.println("Inserte el nombre comun");
					a.setNombreComun(escanea.nextLine());

					System.out.println("Inserte el nombre cientifico");
					a.setNombreCientifico(escanea.nextLine());

					System.out.println("¿En que habitat se encuentra (id)?");
					h.setId(Integer.parseInt(escanea.nextLine()));
					a.setHabitat(h);
					
					System.out.println("Inserte la altura");
					a.setAltura(Integer.parseInt(escanea.nextLine()));

					System.out.println("Inserte el origen");
					a.setOrigen(escanea.nextLine());
					
					System.out.println("Es singular (introduce un 1)");
					a.setSingular(Boolean.parseBoolean(escanea.nextLine()));

					System.out.println("Inserte la fecha (yyyy-MM-dd)");
					a.setFecha(Date.valueOf(escanea.nextLine()));


					insert(a);
					break;

				case READ:
					visualizar();
					break;
				case SALIR:
					System.out.println("ADIOS");
					break;

				default:
					System.out.println("Opcion no valida");
					break;
				}
			} while (select != 0);

		} catch (ClassNotFoundException e) {
			System.out.println("Error de importacion de la libreria");
			e.printStackTrace();
		}

		catch (SQLException e) {
			System.out.println("Error al conectarse a la base de datos");
			e.printStackTrace();
		}
	}

	private static void menu() {
		System.out.println("\n--Menu--");
		System.out.println(CREATE + "-Create");
		System.out.println(READ + "-Read");
		System.out.println(SALIR + "-Salir");
	}

	private static void visualizar() {
		// Variables
		ArrayList<Arbol> arboles = new ArrayList<>();
		int select = 0;

		// Preguntar uno o todos
		System.out.println("1-Visualizar un arbol");
		System.out.println("2-Visualizar todos");
		select = Integer.parseInt(escanea.nextLine());

		if (select == 1) {
			// pedir id
			System.out.println("Inserte el id");
			int id = Integer.parseInt(escanea.nextLine());
			Arbol a = arbol(id);

			// Pintar arbol
			System.out.println(a);

		} else if (select == 2) {
			arboles = arboles();

			for (Arbol a : arboles) {
				System.out.println(a.toString());
			}

		} else {
			System.out.println("Opcion no valida");
		}
	}

	private static void insert(Arbol a) {

		try {
			// Ejecutar query para habitat
	

			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + BBDD, USUARIO,
					CONTRASENIA);

			String sql = "INSERT INTO arboles (nombre_comun, nombre_cientifico,id_habitat, altura, origen, singular, fecha) VALUES ( ?, ?, ?, ?, ?, ?, ?)";
			PreparedStatement pst = conexion.prepareStatement(sql);
			// Ejecutar Query para arbol
			pst.setString(1, a.getNombreComun());
			pst.setString(2, a.getNombreCientifico());
			pst.setInt(3, a.getHabitat().getId());
			pst.setInt(4, a.getAltura());
			pst.setString(5, a.getOrigen());
			pst.setBoolean(6, a.isSingular());
			pst.setDate(7, new java.sql.Date(a.getFecha().getTime()));
			
			pst.execute();

			System.out.println("Insert completado");
		} catch (SQLException e) {
			System.out.println("Error en la Query");
			e.printStackTrace();
		}
	}

	private static ArrayList<Arbol> arboles() {

		ArrayList<Arbol> arboles = new ArrayList<>();

		try {
			Class.forName("com.mysql.cj.jdbc.Driver");

			// crear conexion
			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + BBDD, USUARIO,
					CONTRASENIA);
			// crear statement
			Statement st = conexion.createStatement();

			// ejecutar la consulta y recibir el resultado
			ResultSet resultado = st
					.executeQuery("select * from arboles a inner join habitats h on a.id_habitat = h.id");

			while (resultado.next()) {

				Arbol a = new Arbol();
				Habitat h = new Habitat();
				a.setId(resultado.getInt("id"));
				a.setNombreComun(resultado.getString("nombre_comun"));
				a.setNombreCientifico(resultado.getString("nombre_cientifico"));
				a.setAltura(resultado.getInt("altura"));
				a.setOrigen(resultado.getString("origen"));

				h.setId(resultado.getInt("id_habitat"));
				h.setNombre(resultado.getString("nombre"));
				a.setHabitat(h);

				arboles.add(a);

			}
		} catch (SQLException | ClassNotFoundException e) {
			System.out.println("Error al cargar la tabla");
			e.printStackTrace();
		}
		return arboles;
	}

	private static Arbol arbol(int id) {
		// Variable
		Arbol a = new Arbol();
		Habitat h = new Habitat();
		try {
			// Variables
			Connection conexion = DriverManager.getConnection("jdbc:mysql://" + HOST + "/" + BBDD, USUARIO,
					CONTRASENIA);

			String sql = "SELECT * FROM arboles a INNER JOIN habitat h ON a.habitat_id = h.id WHERE a.id= ?";

			PreparedStatement pst = conexion.prepareStatement(sql);
			// buscar id
			pst.setInt(1, id);
			ResultSet rs = pst.executeQuery();
			rs.next();

			// Darle atributos al arbol

			a.setId(rs.getInt("id"));
			a.setNombreComun(rs.getString("nombre_comun"));
			a.setNombreCientifico(rs.getString("nombre_cientifico"));

			h.setId(rs.getInt("habitat_id"));
			h.setNombre(rs.getString("nombre"));
			a.setHabitat(h);

			a.setAltura(rs.getInt("altura"));
			a.setOrigen(rs.getString("origen"));

			a.setSingular(rs.getBoolean("singular"));

		} catch (SQLException e) {
			System.out.println("Id no encontrado");
			e.printStackTrace();
		}

		return a;
	}
}
