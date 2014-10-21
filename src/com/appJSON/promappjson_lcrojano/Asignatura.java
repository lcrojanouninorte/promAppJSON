package com.appJSON.promappjson_lcrojano;

import java.text.DecimalFormat;
import java.util.ArrayList;



public class Asignatura {
	private String nombre;
	private int creditos;
	private float nota_real;
	private float nota_requerida;
	private float nota_simulada;
	private long semesterId;
	private long id;
	private String estado;
	private int porcentaje = 0;
	private Evaluation[] evaluaciones;
	private String nrc;
	private ArrayList<String> nrcList;
	

	public Asignatura() {
		this.estado = "En Progreso";
		this.nombre = "";
	}
	public String getNombre() {
		return nombre;
	}
	public boolean setNombre(String nombre) {
		if(!nombre.equals("") ){
			this.nombre = nombre;
		}else{
			return false;
		}
		return true;
	}
	public int getCreditos() {
		return this.creditos;
	}
	public boolean setCreditos(String cred) {
		if(!cred.equals("")){
			int creditos = Integer.parseInt(cred);
			if(creditos >=0){
				this.creditos = creditos;
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
		public boolean setNotaRequerida(String nota) {
		if(!nota.equals("")){
			float prom = Float.parseFloat(nota);
			if(prom>=0 && prom<=5){
				this.nota_requerida = roundTwoDecimals(prom);
				
			}else{
				return false;
			}
			return true;
		}else{
			return false;
		}
		
	}
		public void setSemestreID(long id) {
			this.semesterId = id;
			
		}
		public long getID() {
			// TODO Auto-generated method stub
			return this.id;
		}
		public void setID(long id) {
			// TODO Auto-generated method stub
			this.id =  id;
		}
		public String getEstado() {
			if(this.estado == null){
				this.estado = "";
			}
			return this.estado;
		}
		
	
		public void setEstado(String estado) {

			this.estado = estado;
		}
		public float getNotaRequerida() {
			
			return this.nota_requerida;
		}
		public float getNotaReal() {
			// TODO Auto-generated method stub
			return this.nota_real;
		}
		public float getNotaSimulada() {
			// TODO Auto-generated method stub
			return this.nota_simulada;
		}
		public boolean setNota_simulada(String nota_simulada) {
			if(!nota_simulada.equals("")){
				float prom = Float.parseFloat(nota_simulada);
				if(prom>=0 && prom<=5){
					this.nota_simulada= roundTwoDecimals(prom);
				}else{
					return false;
				}
				return true;
			}else{
				return false;
			}
		}
		public int getPorcentaje() {
			
			return this.porcentaje;
		}
		public float getDiferencia() {
			// TODO Auto-generated method stub
			return this.nota_requerida - this.nota_simulada;
		}
		public boolean setNotaReal(String nota) {
			if(!nota.equals("")){
				float prom = Float.parseFloat(nota);
				if(prom>=0 && prom<=5){
					this.nota_real= roundTwoDecimals( prom);
				}else{
					return false;
				}
				return true;
			}else{
				return false;
			}
			
		}
		
		public void setPorcentaje() {
			//TODO:
			//calcular porcentaje basado en las evaluaciones
		}
public long getSemesterId() {
	return semesterId;
}
		
public void setEvaluaciones(Evaluation[] evaluaciones) {
	this.evaluaciones = evaluaciones;
}
public Evaluation[] getEvaluaciones() {
	return evaluaciones;
}
float roundTwoDecimals(float d)
{
    DecimalFormat twoDForm = new DecimalFormat("#.##");
    return Float.valueOf(twoDForm.format(d));
}
public void setNRC(String nrc) {
	// TODO Auto-generated method stub
	this.nrc = nrc;
	
}
public String getNRC(){
	return this.nrc;
}
public void setNRC(ArrayList<String> nrcList) {
	// TODO Auto-generated method stub
	this.nrcList = nrcList;
	
}
public ArrayList<String> getNRCList(){
	return this.nrcList;
}
}


