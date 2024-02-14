package Clases;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Arbol {

	private int id;
	private String nombreComun;
	private String nombreCientifico;
	private int altura;
	private String origen;
	private boolean singular;
	private Date fecha;
	private Habitat habitat;
	
	//Constructor
	public Arbol () {
	}
	
	
	public Arbol(int id, String nombreComun, String nombreCientifico, int altura, String origen,boolean singular, Date fecha,Habitat habitat) {
		this.id = id;
		this.nombreComun = nombreComun;
		this.nombreCientifico = nombreCientifico;
		this.altura = altura;
		this.origen = origen;
		this.singular = singular;
		this.fecha = fecha;
		this.habitat = habitat;
	}

	//Getters y setters
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getNombreComun() {
		return nombreComun;
	}
	public void setNombreComun(String nombreComun) {
		this.nombreComun = nombreComun;
	}
	public String getNombreCientifico() {
		return nombreCientifico;
	}
	public void setNombreCientifico(String nombreCientifico) {
		this.nombreCientifico = nombreCientifico;
	}
	public Habitat getHabitat() {
		return habitat;
	}
	public void setHabitat(Habitat habitat) {
		this.habitat = habitat;
	}
	public int getAltura() {
		return altura;
	}
	public void setAltura(int altura) {
		this.altura = altura;
	}
	public String getOrigen() {
		return origen;
	}
	public boolean isSingular() {
		return singular;
	}


	public void setSingular(boolean singular) {
		this.singular = singular;
	}


	public Date getFecha() {
		return fecha;
	}


	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}


	public void setOrigen(String origen) {
		this.origen = origen;
	}
	@Override
	public String toString() {
		return id + "-Nombre Comun=" + nombreComun + ", Nombre Cientifico=" + nombreCientifico
				 + ", Altura=" + altura + ", Origen=" + origen + ", habitat=" + habitat.getNombre();
	}
	
}
